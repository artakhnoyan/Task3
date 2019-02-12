package com.art.task3.data

import android.arch.lifecycle.MutableLiveData

interface CalcRepository {
    fun calculate(expression: String, result: MutableLiveData<Float>)
}

class CalcRepositoryImpl : CalcRepository {
    private val calculator = Calculator()

    override fun calculate(expression: String, result: MutableLiveData<Float>) {
        result.postValue(calculator.calculate(expression))
    }
}