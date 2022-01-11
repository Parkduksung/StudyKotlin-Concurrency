package com.example.chapter4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chapter4.model.Article
import com.example.chapter4.model.Feed
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : ComponentActivity() {

    private val factory = DocumentBuilderFactory.newInstance()

    private val dispatcher = newFixedThreadPoolContext(2, "IO")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            launch(Main) {
                setContent {
                    MaterialTheme {
                        Articles(list = articles)
                    }
                }
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

    @Composable
    private fun Articles(list: List<Article>) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = list) { item ->
                Article(item = item)
            }
        }
    }

    @Composable
    private fun Article(item: Article) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(item = item)
        }
    }

    @Composable
    fun CardContent(item: Article) {

        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = item.feed,
                    style = (MaterialTheme.typography.h3).copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = item.title,
                    style = (MaterialTheme.typography.h4).copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = item.summary,
                )
            }
        }
    }

}
