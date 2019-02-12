package com.art.task3.presenter

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.art.task3.domain.CalcUseCase

class CalcViewModelFactory(private val calcUseCase: CalcUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalcViewModel::class.java)) {
            val viewModel = CalcViewModel(calcUseCase)
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown CalcViewModel class")
    }
}