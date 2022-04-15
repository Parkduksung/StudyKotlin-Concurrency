package com.example.practice1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.practice1.R
import com.example.practice1.api.BookItem
import com.example.practice1.databinding.FragmentBookDetailBinding
import com.google.android.material.checkbox.MaterialCheckBox

private const val ARG_PARAM_BOOK_ITEM = "arg_param_book_item"

class BookDetailFragment : Fragment() {
    private var bookItem: BookItem? = null

    private lateinit var bookViewModel: BookViewModel

    private lateinit var binding: FragmentBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookItem = it.getParcelable(ARG_PARAM_BOOK_ITEM)
        }
        bookViewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_detail, container, false)

        bookItem?.let { item ->
            binding.item = item
            Glide.with(binding.root)
                .load(item.thumbnail)
                .centerCrop()
                .into(binding.image)
        }

        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.like.setOnCheckedChangeListener { _, b ->
            bookItem?.let {
                bookViewModel.toggle(it)
            }
        }

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