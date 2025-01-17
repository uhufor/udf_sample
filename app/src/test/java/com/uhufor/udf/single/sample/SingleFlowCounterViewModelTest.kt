package com.uhufor.udf.single.sample

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SingleFlowCounterViewModelTest {

    // 코루틴 테스트용 디스패처
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SingleFlowCounterViewModel

    @Before
    fun setUp() {
        // 코루틴 디스패처를 테스트용으로 설정
        Dispatchers.setMain(testDispatcher)
        viewModel = SingleFlowCounterViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isCorrect() {
        // When
        val initialState = viewModel.getInitialState()

        // Then
        assertThat(initialState.count).isEqualTo(0)
    }

    @Test
    fun handleOnClickIncrement_incrementsCount() = runTest {
        // When
        viewModel.handleEvent(SingleFlowCounterEvent.OnClickIncrement)

        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.state.value

        // Then
        assertThat(currentState.count).isEqualTo(1)
    }

    @Test
    fun handleOnClickDecrement_decrementsCountWhenGreaterThanZero() = runTest {
        // Given
        viewModel.handleEvent(SingleFlowCounterEvent.OnClickIncrement)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.handleEvent(SingleFlowCounterEvent.OnClickDecrement)
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.state.value

        // Then
        assertThat(currentState.count).isEqualTo(0)
    }

    @Test
    fun handleOnClickDecrement_whenCountIsZero_emitsShowErrorEffect() = runTest {
        // When
        viewModel.handleEvent(SingleFlowCounterEvent.OnClickDecrement)

        viewModel.effect.test {
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val emittedEffect = awaitItem()
            assertThat(emittedEffect).isInstanceOf(SingleFlowCounterEffect.ShowError::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
