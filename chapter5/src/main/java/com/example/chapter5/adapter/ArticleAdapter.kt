package com.example.chapter5.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter5.adapter.viewholder.ArticleLoaderListener
import com.example.chapter5.adapter.viewholder.ArticleViewHolder
import com.example.chapter5.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleAdapter(private val loaderListener: ArticleLoaderListener) :
    RecyclerView.Adapter<ArticleViewHolder>() {

    private val articleItemList = mutableListOf<Article>()

    private var loading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (!loading && position >= articleItemList.size - 2) {
            loading = true
            CoroutineScope(Dispatchers.IO).launch {
                loaderListener.loadMore()
                loading = false
            }
        }
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