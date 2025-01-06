package com.uhufor.udf.single.sample

import android.util.Log
import com.uhufor.udf.single.SingleFlowUiEvent
import com.uhufor.udf.single.SingleFlowViewModel

class SingleFlowCounterViewModel(
) : SingleFlowViewModel<SingleFlowCounterEvent, SingleFlowCounterEffect, SingleFlowCounterState>() {

    override fun getInitialState(): SingleFlowCounterState = SingleFlowCounterState(count = 0)

    override fun handleEvent(event: SingleFlowCounterEvent) {
        when (event) {
            is SingleFlowCounterEvent.OnClickDecrement -> {
                handleOnClickDecrement()
            }
            else -> {
                super.handleEvent(event)
            }
        }
    }

    @SingleFlowUiEvent(SingleFlowCounterEvent.OnClickIncrement::class)
    private fun handleOnClickIncrement(event: SingleFlowCounterEvent.OnClickIncrement) {
        reduce { copy(count = count + 1) }
    }

    private fun handleOnClickDecrement() {
        if (state.value.count > 0) {
            reduce { copy(count = count - 1) }
        } else {
            SingleFlowCounterEffect.ShowError.emit()
        }
    }
}
