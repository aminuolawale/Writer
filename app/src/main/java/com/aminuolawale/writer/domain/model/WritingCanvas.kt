package com.aminuolawale.writer.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "writing_canvas")
data class WritingCanvas(
    @PrimaryKey val id: Int? = null,
    val title : String = "Untitled",
    val lines: List<Line>,
    val dateCreated: Long,
    val lastUpdated: Long
)

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp

)