package com.example.practice1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practice1.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.addFragmentOnAttachListener { fragmentManager, _ ->
            for (fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible) {
                    when (fragment) {
                        is BookSearchFragment -> {
                            title = "Book Search"
                        }
                        is BookDetailFragment -> {
                            title = "Book Detail"
                        }
                    }
                }
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            for (fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible) {
                    when (fragment) {
                        is BookSearchFragment -> {
                            title = "Book Search"
                        }
                        is BookDetailFragment -> {
                            title = "Book Detail"
                        }
                    }
                }
            }
        }
    }


}