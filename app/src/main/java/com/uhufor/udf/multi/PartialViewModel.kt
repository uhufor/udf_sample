@file:Suppress("MemberVisibilityCanBePrivate")

package com.uhufor.udf.multi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhufor.udf.dispatcher.GeneratedUiEventDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class PartialViewModel<UiEvent, UiEffect, UiState>(
    owner: ViewModel,
    initialState: UiState,
) : EffectStateOwner<UiEffect, UiState> by DefaultEffectStateOwner(initialState) {
    private val coroutineScope: CoroutineScope = owner.viewModelScope
    private val event: MutableSharedFlow<UiEvent> = MutableSharedFlow()

    abstract val eventDispatcher: GeneratedUiEventDispatcher

    init {
        observeEvent()
    }

    private fun observeEvent() {
        launch {
            event.collect(::handleEvent)
        }
    }

    private fun handleEvent(event: UiEvent) {
        eventDispatcher.dispatchEvent(instance = this, event = event)
    }

    fun submit(event: UiEvent) {
        launch {
            this@PartialViewModel.event.emit(event)
        }
    }

    protected fun UiEffect.emit() {
        launch {
            setEffect(this@emit)
        }
    }

    protected fun reduce(block: UiState.() -> UiState) {
        launch {
            setState(block.invoke(state.value))
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }
}
