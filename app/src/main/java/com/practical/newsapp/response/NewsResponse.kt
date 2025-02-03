package com.practical.newsapp.response

import com.practical.newsapp.model.Article


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)