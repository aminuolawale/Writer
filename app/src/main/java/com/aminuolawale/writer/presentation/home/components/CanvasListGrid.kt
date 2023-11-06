package com.aminuolawale.writer.presentation.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aminuolawale.writer.domain.model.WritingCanvas

@Composable
fun CanvasListGrid(canvasList: List<WritingCanvas>, onItemClick: (Int)->Unit, onItemLongClick: (Int) ->Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 240.dp),
        contentPadding = PaddingValues(10.dp),
        modifier =  Modifier.fillMaxSize()
    ) {
        items(canvasList) {
            CanvasItem(
                title = it.title,
                lastUpdated  = it.lastUpdated,
                onClick = {onItemClick(it.id!!)}, onLongClick = {onItemLongClick(it.id!!)}
            )

        }
    }
}