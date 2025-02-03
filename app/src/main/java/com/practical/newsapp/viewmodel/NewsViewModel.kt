package com.practical.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.practical.newsapp.model.Article
import com.practical.newsapp.repository.NewsRepository
import com.practical.newsapp.roomdb.NewsDatabase
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, NewsDatabase::class.java, "news_db")
        .addMigrations(NewsDatabase.MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()

    private val repository = NewsRepository(db, application.applicationContext)

    val news: LiveData<List<Article>> = repository.getAllNews()

    fun fetchNews(apiKey: String) {
        viewModelScope.launch {
            repository.fetchNews(apiKey)
        }
    }

    fun refreshNews(apiKey: String) {
        viewModelScope.launch {
            repository.fetchNews(apiKey) // Fetch latest news
        }
    }


}