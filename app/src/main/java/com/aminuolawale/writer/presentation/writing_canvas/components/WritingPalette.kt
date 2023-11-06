package com.aminuolawale.writer.presentation.writing_canvas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun WritingPalette( modifier: Modifier, onDrawClick: () -> Unit, onEraseClick: () -> Unit
) {
    Surface(color = Color.Gray, modifier = modifier.shadow(elevation= 6.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            ) {
            Button(
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp),
                onClick = onDrawClick) {}
            Button(
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp),
                onClick = onEraseClick) {}

        }

    }
}