package com.example.chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter4.model.Feed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.newFixedThreadPoolContext
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

    private fun asyncFetchHeadlines(feed: Feed, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed.url)
            val news = xml.getElementsByTagName("channel").item(0)
            (0 until news.childNodes.length).map { news.childNodes.item(it) }
                .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
                .filter { "item" == it.tagName }
                .map { it.getElementsByTagName("title").item(0).textContent }
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