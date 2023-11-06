package com.aminuolawale.writer.presentation.writing_canvas

import com.aminuolawale.writer.domain.model.Line
import com.aminuolawale.writer.domain.model.WritingCanvas

data class WritingCanvasState (
    val writingCanvas: WritingCanvas  = WritingCanvas(),
    val isErasing: Boolean = false
)

