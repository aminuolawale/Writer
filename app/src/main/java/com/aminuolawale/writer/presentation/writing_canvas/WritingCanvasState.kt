package com.aminuolawale.writer.presentation.writing_canvas

import com.aminuolawale.writer.domain.model.Line

data class WritingCanvasState (
    val lines: List<Line> = emptyList(),
    val isErasing: Boolean = false
)

