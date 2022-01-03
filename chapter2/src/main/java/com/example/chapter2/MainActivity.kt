package com.example.chapter2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private val dispatcher = newSingleThreadContext(name = "ServiceCall")
    private val factory = DocumentBuilderFactory.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asyncLoadNews()
    }

    private fun asyncLoadNews() = GlobalScope.launch(dispatcher) {
        val resultSize = fetchRssHeadlines().size
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(
                this@MainActivity,
                resultSize.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fetchRssHeadlines(): List<String> {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse("https://www.npr.org/rss/rss.php?id=1001")
        val news = xml.getElementsByTagName("channel").item(0)
        return (0 until news.childNodes.length).map { news.childNodes.item(it) }
            .filter { Node.ELEMENT_NODE == it.nodeType }.map { it as Element }
            .filter { "item" == it.tagName }
            .map { it.getElementsByTagName("title").item(0).textContent }
    }

}