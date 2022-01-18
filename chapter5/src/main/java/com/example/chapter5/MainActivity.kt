package com.example.chapter5

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter5.adapter.ArticleAdapter
import com.example.chapter5.adapter.viewholder.ArticleLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ArticleLoader {

    private val articleAdapter by lazy { ArticleAdapter(this) }

    override suspend fun loadMore() {
        val producer = ArticleProducer.producer

        if (!producer.isClosedForReceive) {
            Log.d("결과", "loadMore")
            val articles = producer.receive()

            GlobalScope.launch(Dispatchers.Main) {
                findViewById<ProgressBar>(R.id.progress).isVisible = false
                articleAdapter.addAll(articles)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.rv_article).adapter = articleAdapter

        GlobalScope.launch {
            loadMore()
        }
    }
}