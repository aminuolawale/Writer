package com.aminuolawale.writer.di

import android.app.Application
import androidx.room.Room
import com.aminuolawale.writer.data.data_source.Converters
import com.aminuolawale.writer.data.data_source.WriterDatabase
import com.aminuolawale.writer.data.repository.WritingCanvasRepositoryImpl
import com.aminuolawale.writer.domain.repository.WritingCanvasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesWriterDatabase(application: Application): WriterDatabase {
        return Room.databaseBuilder(
            application,
            WriterDatabase::class.java,
            WriterDatabase.DATABASE_NAME
        )
            .build()
    }


    @Provides
    @Singleton
    fun providesWritingCanvasRepository(writerDatabase: WriterDatabase): WritingCanvasRepository {
        return WritingCanvasRepositoryImpl(writerDatabase.writingCanvasDao)

    }
}

