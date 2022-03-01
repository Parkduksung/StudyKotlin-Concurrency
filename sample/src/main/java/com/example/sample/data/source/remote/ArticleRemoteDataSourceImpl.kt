package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.FeedUtil
import com.example.sample.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class ArticleRemoteDataSourceImpl : ArticleRemoteDataSource {

    private val factory = DocumentBuilderFactory.newInstance()

    override suspend fun getArticles(feed: Feed): Result<List<Article>> = withContext(Dispatchers.IO) {
        try {
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
            return@withContext Result.Success(articleList)
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }

    }
}