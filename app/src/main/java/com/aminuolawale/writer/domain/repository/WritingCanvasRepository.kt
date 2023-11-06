package com.aminuolawale.writer.domain.repository

import com.aminuolawale.writer.domain.model.WritingCanvas
import kotlinx.coroutines.flow.Flow

interface WritingCanvasRepository {
    fun getCanvases(): Flow<List<WritingCanvas>>
    suspend fun getCanvasById(id:Int): WritingCanvas?

    suspend fun insertCanvas(canvas:  WritingCanvas):Long

    suspend fun deleteCanvas(id: Int)
}