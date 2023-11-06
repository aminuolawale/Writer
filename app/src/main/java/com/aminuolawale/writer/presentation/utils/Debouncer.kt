package com.aminuolawale.writer.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(
    private val scope: CoroutineScope,
    private val delayMs: Long,
    private val function: suspend () -> Unit
) {
    var job: Job? = null

    fun execute() {
        job?.cancel()
        job = scope.launch {
            delay(delayMs)
            function()
        }
    }
}