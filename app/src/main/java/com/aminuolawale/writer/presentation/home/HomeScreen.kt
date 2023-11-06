package com.aminuolawale.writer.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aminuolawale.writer.presentation.Screen
import com.aminuolawale.writer.presentation.home.components.Appbar
import com.aminuolawale.writer.presentation.home.components.CanvasListGrid
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.CanvasDeleted -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = "Deleted")
                }
            }
        }
    }
    Scaffold( scaffoldState=scaffoldState, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.WritingCanvasScreen.route) },
            shape = RectangleShape,
            containerColor = Color.Black,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create new canvas",
                tint = Color.White
            )
        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Appbar(
            )
            CanvasListGrid(canvasList = state.value, onItemClick = {
                navController.navigate(Screen.WritingCanvasScreen.route+"?canvasId=$it")
            }, onItemLongClick = {viewModel.onEvent(HomeEvent.DeleteCanvas(it))})
        }
    }
}