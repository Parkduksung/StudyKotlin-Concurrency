package com.example.practice1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practice1.R
import com.example.practice1.api.BookItem

private const val ARG_PARAM_BOOK_ITEM = "arg_param_book_item"

class BookDetailFragment : Fragment() {
    private var bookItem: BookItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookItem = it.getParcelable(ARG_PARAM_BOOK_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_detail, container, false)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(item: BookItem) =
            BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_BOOK_ITEM, item)
                }
            }
    }
}