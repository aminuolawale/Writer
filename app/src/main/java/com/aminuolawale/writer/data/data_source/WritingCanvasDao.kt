package com.aminuolawale.writer.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aminuolawale.writer.domain.model.WritingCanvas
import kotlinx.coroutines.flow.Flow

@Dao
interface WritingCanvasDao {

    @Query("SELECT * FROM writing_canvas")
    fun getCanvases(): Flow<List<WritingCanvas>>

    @Query("SELECT * FROM writing_canvas where id=:id")
    suspend fun getCanvasById(id:Int): WritingCanvas?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCanvas(canvas: WritingCanvas): Long

    @Query("DELETE FROM writing_canvas where id=:id")
    suspend fun deleteCanvas(id:Int)
}