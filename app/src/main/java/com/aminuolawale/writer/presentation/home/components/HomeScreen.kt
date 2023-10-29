package com.aminuolawale.writer.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aminuolawale.writer.presentation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(Screen.WritingCanvasScreen.route)}) {
            Icon(imageVector = Icons.Default.Add , contentDescription = "Create new canvas" )
        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "This is the home-screen")
        }
    }
}