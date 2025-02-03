package com.practical.newsapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practical.newsapp.R
import com.practical.newsapp.model.Article

class NewDetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    private var currentIndex: Int = 0
    private lateinit var newsArticles: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail)

        imageView = findViewById(R.id.fullImageView)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)


        newsArticles = intent.getParcelableArrayListExtra("NEWS_ARTICLES") ?: arrayListOf()
        currentIndex = intent.getIntExtra("CURRENT_INDEX", 0)


        setArticleData(currentIndex)


        prevButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                setArticleData(currentIndex)
            }
        }

        nextButton.setOnClickListener {
            if (currentIndex < newsArticles.size - 1) {
                currentIndex++
                setArticleData(currentIndex)
            }
        }


        updateButtonStates()
    }

    private fun setArticleData(index: Int) {
        val article = newsArticles[index]
        titleTextView.text = article.title
        descriptionTextView.text = article.description ?: "No description available"

        Glide.with(this)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)


        updateButtonStates()
    }

    private fun updateButtonStates() {
        prevButton.isEnabled = currentIndex > 0
        nextButton.isEnabled = currentIndex < newsArticles.size - 1
    }
}