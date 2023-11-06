package com.aminuolawale.writer.data.data_source

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.aminuolawale.writer.domain.model.Line
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun linesToJson(lines: List<Line>): String {
        return Gson().toJson(lines)
    }

    @TypeConverter
    fun jsonToLines(json: String) :List<Line> {
        val typeToken = object : TypeToken<List<Line>>() {}.type
        return Gson().fromJson(json, typeToken)
    }
}