package com.practical.newsapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.practical.newsapp.dao.NewsDao
import com.practical.newsapp.helper.Converters
import com.practical.newsapp.model.Article


@Database(entities = [Article::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE news ADD COLUMN source TEXT DEFAULT '' NOT NULL")
            }
        }
    }
}