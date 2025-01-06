@file:Suppress("MemberVisibilityCanBePrivate")

package com.uhufor.udf.single

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

    abstract fun handleEvent(event: UiEvent)
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
