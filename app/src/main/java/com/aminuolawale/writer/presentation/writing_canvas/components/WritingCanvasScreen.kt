package com.aminuolawale.writer.presentation.writing_canvas.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aminuolawale.writer.presentation.home.components.Appbar
import com.aminuolawale.writer.presentation.writing_canvas.DrawingMode
import com.aminuolawale.writer.presentation.writing_canvas.WritingCanvasEvent
import com.aminuolawale.writer.presentation.writing_canvas.WritingCanvasViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WritingCanvasScreen(
    navController: NavController,
    viewModel: WritingCanvasViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WritingCanvasViewModel.UiEvent.CanvasSaved -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = "Saved")
                }
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier.fillMaxSize()) {
            Appbar()
            WritingCanvasComp(
                lines = state.value.writingCanvas.lines
            ) { line ->
                viewModel.onEvent(WritingCanvasEvent.Draw(line))

            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                WritingPalette(
                    modifier = Modifier.padding(end = 10.dp),
                    onDrawClick = { viewModel.onEvent(WritingCanvasEvent.ChangeDrawMode(DrawingMode.IsDrawing)) },
                    onEraseClick = { viewModel.onEvent(WritingCanvasEvent.ChangeDrawMode(DrawingMode.IsErasing)) })
                Button(onClick = { viewModel.onEvent(WritingCanvasEvent.Undo) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Undo")
                }
                Button(onClick = { viewModel.onEvent(WritingCanvasEvent.Redo) }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Redo")

                }
            }
        }
    }
}




