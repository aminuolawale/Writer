package com.aminuolawale.writer.presentation.writing_canvas.utils

import com.aminuolawale.writer.domain.model.Line


// TODO: Initialize a list with the CACHE_SIZE
class HistoryStack<T>(private val updater: (List<T>) -> Unit) {
    private var cache: MutableList<T> = mutableListOf()
    private var source: List<T> = emptyList()
    fun undo() {
        if (cache.size < CACHE_SIZE && source.isNotEmpty()) {
            cache.add(source.last())
            updater(source.subList(0, source.size-1))
        }
    }

    fun redo() {
        if (cache.isNotEmpty()) {
            updater(source + cache.last())
            cache = cache.subList(0, cache.size - 1)
        }
    }
    fun updateSource(source: List<T>) {
        this.source = source
    }

    private companion object {
        const val CACHE_SIZE = 50
    }
}