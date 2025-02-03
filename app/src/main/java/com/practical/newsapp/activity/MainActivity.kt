package com.practical.newsapp.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practical.newsapp.R
import com.practical.newsapp.viewmodel.NewsViewModel
import com.practical.newsapp.adapter.NewsAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        val noDataTextView = findViewById<TextView>(R.id.noDataTextView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NewsAdapter()
        recyclerView.adapter = adapter

        viewModel.news.observe(this) { news ->
            adapter.submitList(news)


            noDataTextView.visibility = if (news.isEmpty()) View.VISIBLE else View.GONE
        }


        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshNews(getString(R.string.news_api_key))
            swipeRefreshLayout.isRefreshing = false
        }


        viewModel.fetchNews(getString(R.string.news_api_key))
    }
}