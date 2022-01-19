package com.example.chapter5.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter5.R
import com.example.chapter5.model.Article

class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
) {
    private val feed = itemView.findViewById<TextView>(R.id.feed)
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val summary = itemView.findViewById<TextView>(R.id.summary)


    fun bind(item: Article) {
        feed.text = item.feed
        title.text = item.title
        summary.text = item.summary
    }
}

interface ArticleLoaderListener {
    suspend fun loadMore()
}