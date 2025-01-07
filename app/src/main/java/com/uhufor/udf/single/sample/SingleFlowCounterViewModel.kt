package com.uhufor.udf.single.sample

import com.uhufor.udf.annotation.SingleFlowUiEvent
import com.uhufor.udf.single.SingleFlowViewModel

class SingleFlowCounterViewModel(
) : SingleFlowViewModel<SingleFlowCounterEvent, SingleFlowCounterEffect, SingleFlowCounterState>() {

    override fun getInitialState(): SingleFlowCounterState = SingleFlowCounterState(count = 0)

    override fun handleEvent(event: SingleFlowCounterEvent) {
        when (event) {
//            is SingleFlowCounterEvent.OnClickDecrement -> {
//                handleOnClickDecrement()
//            }
            else -> {
                if (true) {
                    SingleFlowCounterViewModel_Dispatcher.dispatchEvent(this, event)
                } else {
                    super.handleEvent(event)
                }
            }
        }
    }

    @SingleFlowUiEvent(SingleFlowCounterEvent.OnClickIncrement::class)
    fun handleOnClickIncrement(event: SingleFlowCounterEvent.OnClickIncrement) {
        reduce { copy(count = count + 1) }
    }

    @SingleFlowUiEvent(SingleFlowCounterEvent.OnClickDecrement::class)
    fun handleOnClickDecrement() {
        if (state.value.count > 0) {
            reduce { copy(count = count - 1) }
        } else {
            SingleFlowCounterEffect.ShowError.emit()
        }
    }
}
