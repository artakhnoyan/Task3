package com.art.task3.presenter

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.art.task3.data.CalcRepositoryImpl
import com.art.task3.domain.CalcUseCase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

internal class CalcViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun onDigitClick() {
        val input = "4"
        val calcViewModel = getCalcViewModel()
        calcViewModel.onDigitClick(input)
        assertEquals(calcViewModel.previewText.value, input)
    }

    @Test
    fun onOperatorClick() {
        val input = "-"
        val calcViewModel = getCalcViewModel()
        calcViewModel.onOperatorClick(input)
        assertEquals(input, calcViewModel.previewText.getSafeValue())
    }

    @Test
    fun onAddOperatorClick() {
        val input = "+"
        val calcViewModel = getCalcViewModel()
        calcViewModel.onOperatorClick(input)
        assertEquals("", calcViewModel.previewText.getSafeValue())
    }

    @Test
    fun setResultText() {
        val calcViewModel = getCalcViewModel()
        calcViewModel.onDigitClick("4")
        calcViewModel.onOperatorClick("+")
        calcViewModel.onDigitClick("4")
        calcViewModel.setResultToExpression()
        assertEquals("8.0", calcViewModel.calcResult.getSafeValue())
    }

    @Test
    fun removeLastCharacter() {
        val input = "45"
        val calcViewModel = getCalcViewModel()
        calcViewModel.onDigitClick(input)
        calcViewModel.removeLastCharacter()
        assertEquals("4", calcViewModel.previewText.getSafeValue())
    }

    @Test
    fun clearExpression() {
        val input = "5"
        val calcViewModel = getCalcViewModel()
        calcViewModel.onDigitClick(input)
        calcViewModel.clearExpression()
        assertEquals("", calcViewModel.previewText.getSafeValue())
    }

    private fun getCalcViewModel(): CalcViewModel = CalcViewModel(CalcUseCase(CalcRepositoryImpl()))
}

@Throws(InterruptedException::class)
private fun <T> LiveData<T>.getSafeValue(): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            removeObserver(this)
        }
    }
    observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    return data
}