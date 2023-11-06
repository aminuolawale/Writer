package com.aminuolawale.writer.data.repository

import com.aminuolawale.writer.data.data_source.WritingCanvasDao
import com.aminuolawale.writer.domain.model.WritingCanvas
import com.aminuolawale.writer.domain.repository.WritingCanvasRepository
import kotlinx.coroutines.flow.Flow


class WritingCanvasRepositoryImpl (private val writingCanvasDao: WritingCanvasDao) :
    WritingCanvasRepository {
    override fun getCanvases(): Flow<List<WritingCanvas>> = writingCanvasDao.getCanvases()

    override suspend fun getCanvasById(id: Int): WritingCanvas? = writingCanvasDao.getCanvasById(id)

    override suspend fun insertCanvas(canvas: WritingCanvas) = writingCanvasDao.insertCanvas(canvas)

    override suspend fun deleteCanvas(id: Int) = writingCanvasDao.deleteCanvas(id)
}