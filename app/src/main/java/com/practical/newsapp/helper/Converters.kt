package com.practical.newsapp.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practical.newsapp.model.Source

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String?): Source? {
        val type = object : TypeToken<Source>() {}.type
        return gson.fromJson(sourceString, type)
    }
}