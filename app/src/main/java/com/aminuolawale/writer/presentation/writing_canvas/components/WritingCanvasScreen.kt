package com.aminuolawale.writer.presentation.writing_canvas.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WritingCanvasScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "This is the writing canvas screen")
    }
    val colors = listOf(Color.Green, Color.Blue, Color.Yellow, Color.Black)
    val lines = remember {
        mutableStateListOf<Line>()
    }
    var color by remember {
        mutableStateOf(Color.Black)
    }
    var isErasing by remember {
        mutableStateOf(false)
    }
    Canvas(modifier = Modifier
        .fillMaxHeight(0.95F)
        .fillMaxWidth()
        .background(color = Color.White)
        .pointerInput(true) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                val line = Line(
                    start = change.position - dragAmount,
                    end = change.position,
                    color = if (isErasing) Color.White else color,
                    strokeWidth = if (isErasing) 40.dp else 1.dp
                )
                lines.add(line)
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
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround) {
        repeat(4) {
            Button(
                onClick = {
                    color = colors[it]
                    isErasing = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors[it],
                    contentColor = Color.White,
                    Color.Gray,
                    Color.Gray
                )
            ) {

            }
        }
        Button(
            onClick = { isErasing = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Gray,
                Color.Gray,
                Color.Gray
            )
        ) {

        }

    }
}


data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)