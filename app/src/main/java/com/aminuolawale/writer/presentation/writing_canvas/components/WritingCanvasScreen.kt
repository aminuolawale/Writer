package com.aminuolawale.writer.presentation.writing_canvas.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aminuolawale.writer.presentation.home.components.Appbar
import com.aminuolawale.writer.presentation.writing_canvas.DrawingMode
import com.aminuolawale.writer.domain.model.Line
import com.aminuolawale.writer.presentation.writing_canvas.WritingCanvasEvent
import com.aminuolawale.writer.presentation.writing_canvas.WritingCanvasViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WritingCanvasScreen(
    navController: NavController,
    viewModel: WritingCanvasViewModel = hiltViewModel()
) {
    val state  = viewModel.state
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 =true ){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is WritingCanvasViewModel.UiEvent.CanvasSaved -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = "Saved")
                }
            }
        }
    }
    Scaffold(scaffoldState= scaffoldState) {
        Column(modifier = Modifier.fillMaxSize()) {
            Appbar()
            Canvas(modifier = Modifier
                .shadow(elevation = 6.dp)
                .fillMaxHeight(0.95F)
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color.White)
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val line = Line(
                            start = change.position - dragAmount,
                            end = change.position,
                            color = if (state.value.isErasing) Color.White else Color.Black,
                            strokeWidth = if (state.value.isErasing) 40.dp else 1.dp
                        )
                        viewModel.onEvent(WritingCanvasEvent.Draw(line))
                    }
                }, onDraw = {
                state.value.lines.forEach { line ->
                    drawLine(
                        color = line.color,
                        start = line.start,
                        end = line.end,
                        strokeWidth = line.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            })
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                WritingPalette(
                    modifier = Modifier.padding(end = 10.dp),
                    onDrawClick = { viewModel.onEvent(WritingCanvasEvent.ChangeDrawMode(DrawingMode.IsDrawing)) },
                    onEraseClick = { viewModel.onEvent(WritingCanvasEvent.ChangeDrawMode(DrawingMode.IsErasing)) })
                Button(onClick = {viewModel.onEvent(WritingCanvasEvent.SaveCanvas)  }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        tint = Color.Black,
                        contentDescription = "Save Canvas"
                    )
                }
            }
        }
    }
}




