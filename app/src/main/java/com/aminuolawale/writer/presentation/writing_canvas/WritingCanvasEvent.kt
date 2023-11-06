package com.aminuolawale.writer.presentation.writing_canvas

import com.aminuolawale.writer.domain.model.Line

sealed class WritingCanvasEvent () {
    data class Draw(val line: Line) : WritingCanvasEvent()
    data class ChangeDrawMode(val drawingMode: DrawingMode):WritingCanvasEvent()

    object SaveCanvas: WritingCanvasEvent()
}

sealed class DrawingMode() {
   object IsDrawing: DrawingMode()
    object IsErasing: DrawingMode()
}