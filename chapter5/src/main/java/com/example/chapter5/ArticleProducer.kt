package com.example.chapter5

import android.util.Log
import com.example.chapter5.model.Article
import com.example.chapter5.model.Feed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.newFixedThreadPoolContext
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

object ArticleProducer {

    private val feeds = listOf(
        Feed(name = "npr", url = "https://www.npr.org/rss/rss.php?id=1001"),
        Feed(name = "cnn", url = "http://rss.cnn.com/rss/cnn_topstories.rss"),
        Feed(name = "fox", url = "http://feeds.foxnews.com/foxnews/politics?format=xml"),
    )

    private val factory = DocumentBuilderFactory.newInstance()

    private val dispatcher = newFixedThreadPoolContext(2, "IO")

    val producer = GlobalScope.produce(dispatcher) {
        feeds.forEach {
            send(fetchArticles(it))
        }
    }

    private fun fetchArticles(feed: Feed): List<Article> {
        Log.d("결과", "fetchArticles")
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse(feed.url)
        val news = xml.getElementsByTagName("channel").item(0)
        return (0 until news.childNodes.length).map { news.childNodes.item(it) }
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
}