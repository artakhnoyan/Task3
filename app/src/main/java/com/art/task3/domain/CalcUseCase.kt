package com.art.task3.domain

import android.arch.lifecycle.MutableLiveData

class CalcUseCase(private val calcRepository: CalcRepository) : UseCase() {

    operator fun invoke(expression: String, result: MutableLiveData<Float>) {
        execute { calcRepository.calculate(expression, result) }
    }
}