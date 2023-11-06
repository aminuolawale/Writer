package com.aminuolawale.writer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aminuolawale.writer.presentation.home.HomeScreen
import com.aminuolawale.writer.presentation.writing_canvas.components.WritingCanvasScreen
import com.aminuolawale.writer.ui.theme.WriterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriterTheme {
                Surface(color = Color.White) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = Screen.WritingCanvasScreen.route + "?canvasId={canvasId}",
                            arguments = listOf(navArgument(name = "canvasId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            WritingCanvasScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
