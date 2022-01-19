package com.example.chapter5

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter5.adapter.ArticleAdapter
import com.example.chapter5.adapter.viewholder.ArticleLoaderListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ArticleLoaderListener {

    private val articleAdapter by lazy { ArticleAdapter(this) }

    override suspend fun loadMore() {
        val producer = ArticleProducer.producer

        if (!producer.isClosedForReceive) {
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

        val producer = GlobalScope.produce<Any> {
            send(5)
            send("a")
            send("1")
        }

        GlobalScope.launch {
            producer.consumeAsFlow().take(3).collect {
                Log.d("결과", it.toString())
            }
        }

//        findViewById<RecyclerView>(R.id.rv_article).adapter = articleAdapter
//
//        GlobalScope.launch {
//            loadMore()
//        }
    }
}