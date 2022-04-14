package com.example.practice1.ui

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.practice1.api.BookItem

class BookSearchAdapter(
    private val itemClick: (BookItem) -> Unit
) : PagingDataAdapter<BookItem, BookSearchViewHolder>(comparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookSearchViewHolder {
        return BookSearchViewHolder.create(itemClick, parent)
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        Log.d("결과", "$position")
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<BookItem>() {
            override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}