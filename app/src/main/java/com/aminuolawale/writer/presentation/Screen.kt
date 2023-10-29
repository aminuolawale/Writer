package com.aminuolawale.writer.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "home_screen")
    object WritingCanvasScreen: Screen(route = "writing_canvas_screen")
}