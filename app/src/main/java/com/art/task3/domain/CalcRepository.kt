package com.art.task3.domain

import android.arch.lifecycle.MutableLiveData


interface CalcRepository {
    fun calculate(expression: String, result: MutableLiveData<Float>)
}