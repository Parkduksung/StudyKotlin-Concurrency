package com.example.practice1.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice1.R
import com.example.practice1.api.BookItem
import com.example.practice1.databinding.BookSearchItemBinding

class BookSearchViewHolder(
    private val like: (BookItem) -> Unit,
    private val binding: BookSearchItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookItem?) {
        item?.let { item ->
            Glide.with(binding.root)
                .load(item.thumbnail)
                .centerCrop()
                .into(binding.imageView)

            binding.imageView.setOnClickListener {
                like.invoke(item)
            }
        }
    }

    companion object {
        fun create(
            like: (BookItem) -> Unit,
            parent: ViewGroup
        ): BookSearchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_search_item, parent, false)
            val binding = BookSearchItemBinding.bind(view)
            return BookSearchViewHolder(like, binding)
        }
    }
}