package com.example.chapter3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private val factory = DocumentBuilderFactory.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun asyncFetchHeadlines(feed: String, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed)
            val news = xml.getElementsByTagName("channel").item(0)
            (0 until news.childNodes.length).map { news.childNodes.item(it) }
                .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
                .filter { "item" == it.tagName }
                .map { it.getElementsByTagName("title").item(0).textContent }
        }
}