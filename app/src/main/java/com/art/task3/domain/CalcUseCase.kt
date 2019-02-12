package com.art.task3.domain

import android.arch.lifecycle.MutableLiveData
import com.art.task3.data.CalcRepository

class CalcUseCase(private val calcRepository: CalcRepository) {


    operator fun invoke(expression: String, result: MutableLiveData<Float>) {
        calcRepository.calculate(expression, result)
    }
}