package com.uhufor.udf.sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

/*
TODO:
    1. Using Event, Effect, State
    2. Composite Event Reducer
    3. Generate Boiler plate code related with Event
*/
@Suppress("MemberVisibilityCanBePrivate")
abstract class UdfEventReducer<Event, Effect, State>(
    private val initialState: State,
    private val exceptionHandler: UdfCoroutineExceptionHandler = UdfCoroutineExceptionHandler,
) {
    //region Event Sources
    protected lateinit var coroutineScope: CoroutineScope
    protected val event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect>
        get() = _effect

    // TODO: Make it lazy
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state.asStateFlow()
    //endregion

    fun attach(owner: UdfCoroutineScopeOwner) {
        coroutineScope = owner.getCoroutineScope() + exceptionHandler.getCoroutineExceptionHandler()
    }

    //region Event
    private fun observeEvent() {
        launch {
            event.collect(::handleEvent)
        }
    }

    abstract fun handleEvent(event: Event)
    //endregion

    //region Effect
    protected fun Effect.emit() {
        launch {
            _effect.emit(this@emit)
        }
    }
    //endregion

    //region State
    protected fun reduce(block: State.() -> State) {
        launch {
            _state.emit(block.invoke(_state.value))
        }
    }
    //endregion

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }
}
