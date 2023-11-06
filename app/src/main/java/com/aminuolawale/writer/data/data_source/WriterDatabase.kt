package com.aminuolawale.writer.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.aminuolawale.writer.domain.model.WritingCanvas

@Database(entities = [WritingCanvas::class], version = 1)
@TypeConverters(Converters::class)
abstract class WriterDatabase: RoomDatabase() {
    abstract val writingCanvasDao: WritingCanvasDao
    companion object{
        const val DATABASE_NAME = "writer_db1"
    }
}