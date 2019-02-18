package com.art.task3.data

import android.arch.lifecycle.MutableLiveData
import com.art.task3.domain.CalcRepository

class CalcRepositoryImpl : CalcRepository {
    private val calculator = Calculator()

    override fun calculate(expression: String, result: MutableLiveData<Float>) {
        result.postValue(calculator.calculate(expression))
    }
}