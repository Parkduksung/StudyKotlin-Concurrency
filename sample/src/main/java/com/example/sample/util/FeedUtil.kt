package com.example.sample.util

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

object FeedUtil {

    private val factory = DocumentBuilderFactory.newInstance()

    fun getArticles(feed: Feed) : List<Article> {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse(feed.url)
        val news = xml.getElementsByTagName("channel").item(0)
        val articleList = (0 until news.childNodes.length).map { news.childNodes.item(it) }
            .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
            .filter { "item" == it.tagName }
            .map {
                val title = it.getElementsByTagName("title").item(0).textContent
                var summary = it.getElementsByTagName("description").item(0).textContent
                Article(feed = feed.name, title, summary)
            }
        return articleList
    }

}