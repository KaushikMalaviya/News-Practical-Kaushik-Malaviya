package com.practical.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practical.newsapp.R
import com.practical.newsapp.activity.NewDetailActivity
import com.practical.newsapp.model.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.newsTitle)
        private val description: TextView = view.findViewById(R.id.newsDescription)
        private val image: ImageView = view.findViewById(R.id.newsImage)

        fun bind(article: Article) {
            title.text = article.title
            description.text = article.publishedAt?.let { formatPublishedDate(it) } ?: "No date available"


            Glide.with(itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)




            itemView.setOnClickListener {


                val context: Context = itemView.context
                val intent = Intent(context, NewDetailActivity::class.java).apply {
                    putParcelableArrayListExtra("NEWS_ARTICLES", ArrayList(currentList))
                    putExtra("CURRENT_INDEX", adapterPosition)
                }
                context.startActivity(intent)
            }
        }
    }

    private fun formatPublishedDate(publishedAt: String): String {
        val inputFormats = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )

        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        for (format in inputFormats) {
            try {
                val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                val date: Date = inputFormat.parse(publishedAt) ?: continue
                return outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return publishedAt
    }
}
