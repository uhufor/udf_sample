@file:Suppress("MemberVisibilityCanBePrivate")

package com.uhufor.udf.single

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Method

abstract class SingleFlowViewModel<UiEvent, UiEffect, UiState> : ViewModel() {
    protected val event: MutableSharedFlow<UiEvent> = MutableSharedFlow()

    private val _effect: MutableSharedFlow<UiEffect> = MutableSharedFlow()
    val effect: SharedFlow<UiEffect>
        get() = _effect

    private val _state: MutableStateFlow<UiState> by lazy {
        MutableStateFlow(getInitialState())
    }
    val state: StateFlow<UiState>
        get() = _state.asStateFlow()

    private val methodCache: MutableMap<Class<*>, Method> = mutableMapOf()

    init {
        observeEvent()
    }

    //region Event
    private fun observeEvent() {
        launch {
            event.collect(::handleEvent)
        }
    }

    open fun handleEvent(event: UiEvent) {
        handleEventByAnnotation(event)
    }

    private fun handleEventByAnnotation(event: UiEvent) {
        val target = event ?: return

        if (methodCache.isEmpty()) {
            this.javaClass.declaredMethods
                .filter { it.isAnnotationPresent(SingleFlowUiEvent::class.java) }
                .onEach { method ->
                    val annotation = method.getAnnotation(SingleFlowUiEvent::class.java)
                    if (annotation != null) {
                        methodCache[annotation.target.java] = method
                    }
                }
        }

        val method = methodCache[target::class.java]

        runCatching {
            method?.let {
                it.isAccessible = true
                if (it.parameterCount == 0) {
                    it.invoke(this)
                } else {
                    it.invoke(this, event)
                }
            }
        }.onFailure {
            Log.w("udf", "handleEventByAnnotation: ${it.message}")
        }
    }
    //endregion

    //region Effect
    protected fun UiEffect.emit() {
        launch {
            _effect.emit(this@emit)
        }
    }
    //endregion

    //region State
    protected fun reduce(block: UiState.() -> UiState) {
        launch {
            _state.emit(block.invoke(_state.value))
        }
    }

    abstract fun getInitialState(): UiState
    //endregion

    fun submit(event: UiEvent) {
        launch {
            this@SingleFlowViewModel.event.emit(event)
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}
