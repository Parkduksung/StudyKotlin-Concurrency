package com.example.chapter4

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter4.adapter.ArticleAdapter
import com.example.chapter4.model.Article
import com.example.chapter4.model.Feed
import com.example.chapter4.suspend.Exam1
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private val factory = DocumentBuilderFactory.newInstance()

    private val dispatcher = newFixedThreadPoolContext(2, "IO")

    private val articleAdapter = ArticleAdapter()

    private val rvArticle by lazy { findViewById<RecyclerView>(R.id.rv_article) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Exam1.getProfile()
//        initUi()
    }


    private fun initUi() {
        rvArticle.run {
            adapter = articleAdapter
        }
        asyncLoadNews()
    }

    private fun asyncLoadNews() = GlobalScope.launch {
        val requests = mutableListOf<Deferred<List<Article>>>()

        feeds.mapTo(requests) {
            asyncFetchArticles(it, dispatcher)
        }

        requests.forEach {
            it.join()
        }

        val articles = requests
            .filter { !it.isCancelled }
            .flatMap { it.getCompleted() }

        val failedCount = requests
            .filter { it.isCancelled }
            .size

        val obtained = requests.size - failedCount

        launch(Main) {
            findViewById<ProgressBar>(R.id.progress).isVisible = false
            articleAdapter.addAll(articles)
        }

    }

    private fun asyncFetchArticles(feed: Feed, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            delay(1000)
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed.url)
            val news = xml.getElementsByTagName("channel").item(0)
            (0 until news.childNodes.length).map { news.childNodes.item(it) }
                .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
                .filter { "item" == it.tagName }
                .map {
                    val title = it.getElementsByTagName("title").item(0).textContent
                    var summary = it.getElementsByTagName("description").item(0).textContent

                    if (!summary.startsWith("<div") && summary.contains("<div")) {
                        summary = summary.substring(0, summary.indexOf("<div"))
                    } else if (summary.startsWith("<div")) {
                        summary = ""
                    }

                    Article(feed = feed.name, title, summary)
                }
        }

    companion object {

        private val feeds = listOf(
            Feed(name = "npr", url = "https://www.npr.org/rss/rss.php?id=1001"),
            Feed(name = "cnn", url = "http://rss.cnn.com/rss/cnn_topstories.rss"),
            Feed(name = "fox", url = "http://feeds.foxnews.com/foxnews/politics?format=xml"),
            Feed(name = "inv", url = "htt:myNewsFeedError")
        )

    }
}