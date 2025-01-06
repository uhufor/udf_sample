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
    //endregion

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

        val eventMethods = this.javaClass
            .declaredMethods
            .filter { it.annotations.any { annotation -> annotation is SingleFlowUiEvent } }

        val method = eventMethods
            .firstOrNull {
                it.getAnnotation(SingleFlowUiEvent::class.java)?.target == target::class ||
                        (it.parameterCount == 1 && it.parameterTypes[0] == target::class.java)
            }

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
