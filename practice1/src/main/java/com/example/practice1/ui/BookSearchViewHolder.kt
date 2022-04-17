package com.example.practice1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice1.R
import com.example.practice1.api.BookItem
import com.example.practice1.databinding.BookSearchItemBinding

class BookSearchViewHolder(
    private val itemClick: (ItemClickType) -> Unit,
    private val binding: BookSearchItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookItem?) {
        item?.let { item ->
            binding.item = item
            Glide.with(binding.root)
                .load(item.thumbnail)
                .centerCrop()
                .into(binding.image)
            itemView.setOnClickListener {
                itemClick.invoke(ItemClickType.GetItem(item))
            }

            binding.like.setOnCheckedChangeListener { _, _ ->
                itemClick.invoke(ItemClickType.ToggleBookmark(item))
            }
        }
    }

    companion object {
        fun create(
            itemClick: (ItemClickType) -> Unit,
            parent: ViewGroup
        ): BookSearchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_search_item, parent, false)
            val binding = BookSearchItemBinding.bind(view)
            return BookSearchViewHolder(itemClick, binding)
        }
    }
}

sealed class ItemClickType {
    data class ToggleBookmark(val item :BookItem) : ItemClickType()
    data class GetItem(val item :BookItem) : ItemClickType()
}