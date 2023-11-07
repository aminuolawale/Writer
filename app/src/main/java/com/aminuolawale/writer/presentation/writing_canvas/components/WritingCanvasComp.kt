package com.aminuolawale.writer.presentation.writing_canvas.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aminuolawale.writer.domain.model.Line

@Composable
fun WritingCanvasComp(lines: List<Line>,  onPointerDrag:(Line)->Unit) {
    Canvas(modifier = Modifier
        .shadow(elevation =6.dp)
        .fillMaxHeight(0.95F)
        .fillMaxWidth()
        .padding(10.dp)
        .background(color = Color.White)
        .pointerInput(true) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                val line = Line(
                    start = change.position - dragAmount,
                    end = change.position
                )
                onPointerDrag(line)
            }
        }, onDraw = {
        lines.forEach { line ->
            drawLine(
                color = line.color,
                start = line.start,
                end = line.end,
                strokeWidth = line.strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    })
}