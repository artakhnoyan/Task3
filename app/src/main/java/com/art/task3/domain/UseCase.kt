package com.art.task3.domain

import java.util.concurrent.Executors

private const val THREAD_NUMBER: Int = 1

open class UseCase {
    private val executorService = Executors.newFixedThreadPool(THREAD_NUMBER)

    protected fun execute(block: () -> Unit) {
        executorService.execute(block)
    }
}