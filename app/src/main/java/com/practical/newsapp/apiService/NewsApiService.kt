package com.practical.newsapp.apiService

import com.practical.newsapp.model.Article
import com.practical.newsapp.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("sources") sources: String = "bbc-news",
        @Query("apiKey") apiKey: String,
        @Header("User-Agent") userAgent: String = "AndroidNewsApp"
    ): NewsResponse

}


data class NewsResponse(val articles: List<Article>)