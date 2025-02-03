package com.practical.newsapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import com.practical.newsapp.retrofit.RetrofitInstance
import com.practical.newsapp.roomdb.NewsDatabase

class NewsRepository(private val db: NewsDatabase, private val context: Context) {
    private val newsDao = db.newsDao()

    fun getAllNews() = newsDao.getAllNews()

    suspend fun fetchNews(apiKey: String) {
        if (isOnline()) {
            try {
                val response = RetrofitInstance.api.getNews(apiKey = apiKey)
                response.articles?.let { newArticles ->
                    newsDao.clearNews()
                    newsDao.insertAll(newArticles)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }



}