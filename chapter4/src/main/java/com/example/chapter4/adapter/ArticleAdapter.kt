package com.example.chapter4.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter4.adapter.viewholder.ArticleViewHolder
import com.example.chapter4.model.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

    private val articleItemList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleItemList[position])
    }

    override fun getItemCount(): Int =
        articleItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<Article>) {
        articleItemList.addAll(list)
        notifyDataSetChanged()
    }
}