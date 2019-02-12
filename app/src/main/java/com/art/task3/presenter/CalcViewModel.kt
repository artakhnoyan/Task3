package com.art.task3.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.art.task3.domain.CalcUseCase

class CalcViewModel(private val calcUseCase: CalcUseCase) : ViewModel() {
    private var expression: String = ""
    private var result: String = ""

    private val _calcResult = MutableLiveData<Float>()
    val calcResult: LiveData<String>

    private val _previewText = MutableLiveData<String>()
    val previewText: LiveData<String> = _previewText

    init {
        calcResult = Transformations.map(_calcResult) {
            if (!it.isNaN()) {
                result = it.toString()
                result
            } else ""
        }
    }

    fun onDigitClick(input: String) = concatenateAndCalculate(input)

    fun onOperatorClick(input: String) {
        if (expression.isNotEmpty()) {
            if (expression.isLastCharOperator()) {
                removeLastCharacter()
            }
            concatenateAndCalculate(input)
            return
        }
        setOperatorForFirstChar(input)
    }

    private fun setOperatorForFirstChar(input: String) {
        when (input) {
            "(", "-" -> {
                expression = input
                setPreviewText()
            }
            "+" -> {
                removeLastCharacter()
                setPreviewText()
            }
        }
    }

    private fun concatenateAndCalculate(input: String) {
        expression += input
        setPreviewText()
        calculate()
    }

    private fun calculate() {
        calcUseCase.invoke(expression, _calcResult)
    }

    fun setResultToExpression() {
        expression = result
        setPreviewText()
    }

    fun removeLastCharacter() {
        if (expression.isEmpty()) return
        expression = expression.take(expression.lastIndex)
        setPreviewText()
        calculate()
    }

    fun clearExpression() {
        expression = ""
        setPreviewText()
        _calcResult.postValue(Float.NaN)
    }

    private fun setPreviewText() {
        _previewText.postValue(expression)
    }


    private fun String.isLastCharOperator(): Boolean =
        with(this.last()) { this == '-' || this == '+' || this == '*' || this == '/' }
}