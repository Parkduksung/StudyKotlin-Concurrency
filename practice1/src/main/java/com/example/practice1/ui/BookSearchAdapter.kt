package com.example.practice1.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.practice1.api.BookItem

class BookSearchAdapter(
    private val itemClick: (item: BookItem) -> Unit
): PagingDataAdapter<BookItem, BookSearchViewHolder>(comparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookSearchViewHolder {
        return BookSearchViewHolder.create(itemClick, parent)
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun toggle(item: BookItem) {
        if (snapshot().contains(item)) {
            val index = snapshot().indexOfFirst { it == item }
            snapshot()[index]?.let {
                it.bookmark = !it.bookmark
                notifyItemChanged(index)
            }
        }
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