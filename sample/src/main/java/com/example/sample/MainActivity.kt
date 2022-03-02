package com.example.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.sample.data.model.Feed
import com.example.sample.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val articleViewModel by viewModels<ArticleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articleViewModel.getArticles(Feed(name = "npr", url = "https://www.npr.org/rss/rss.php?id=1001"))

        articleViewModel.articleViewStateLiveData.observe(this) { viewState ->
            when (viewState) {
                is ArticleViewModel.ArticleViewState.GetArticles -> {
                    Log.d("결과", viewState.list.toString())
                }

                is ArticleViewModel.ArticleViewState.Error -> {
                }
            }

        }


    }
}