package com.example.chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chapter4.model.Article
import com.example.chapter4.model.Feed
import kotlinx.coroutines.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private val factory = DocumentBuilderFactory.newInstance()

    private val dispatcher = newFixedThreadPoolContext(2, "IO")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    }

    private fun asyncFetchArticles(feed: Feed, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed.url)
            val news = xml.getElementsByTagName("channel").item(0)
            (0 until news.childNodes.length).map { news.childNodes.item(it) }
                .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
                .filter { "item" == it.tagName }
                .map {
                    val title = it.getElementsByTagName("title").item(0).textContent
                    val summary = it.getElementsByTagName("description").item(0).textContent
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