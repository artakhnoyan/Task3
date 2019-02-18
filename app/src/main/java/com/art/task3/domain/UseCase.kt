package com.art.task3.domain

import java.util.concurrent.Executors
import java.util.concurrent.Future

private const val THREAD_NUMBER: Int = 1

open class UseCase {
    private val executorService = Executors.newFixedThreadPool(THREAD_NUMBER)
    private var future: Future<*>? = null

    protected fun execute(block: () -> Unit) {
        future?.cancel(true)
        future = executorService.submit(block)
    }
}