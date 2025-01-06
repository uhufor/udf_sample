package com.uhufor.udf.single.sample

sealed class SingleFlowCounterEvent {
    data object OnClickIncrement : SingleFlowCounterEvent()
    data object OnClickDecrement : SingleFlowCounterEvent()
}

sealed class SingleFlowCounterEffect {
    data object ShowError : SingleFlowCounterEffect()
}

data class SingleFlowCounterState(val count: Int)
