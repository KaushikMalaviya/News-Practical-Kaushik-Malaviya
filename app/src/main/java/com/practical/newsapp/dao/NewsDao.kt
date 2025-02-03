package com.practical.newsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practical.newsapp.model.Article


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("DELETE FROM news")
    suspend fun clearNews()

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<Article>>
}
