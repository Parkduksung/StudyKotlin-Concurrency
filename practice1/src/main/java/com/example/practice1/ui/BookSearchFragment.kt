package com.example.practice1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.practice1.databinding.FragmentBookSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class BookSearchFragment : Fragment() {

    private lateinit var binding: FragmentBookSearchBinding

    private lateinit var bookViewModel: BookViewModel

    private val adapter: BookSearchAdapter = BookSearchAdapter {type ->
        when(type) {
            is ItemClickType.ToggleBookmark -> {
                bookViewModel.toggle(item = type.item)
            }

            is ItemClickType.GetItem -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(binding.containerList.id, BookDetailFragment.newInstance(type.item))
                    .addToBackStack("BookDetailFragment").commitAllowingStateLoss()
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookViewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookSearchBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            bookViewModel.pagingDataFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        requireActivity().lifecycleScope.launchWhenCreated {
            bookViewModel.toggleItem.collectLatest {
                adapter.toggle(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.outlinedTextField.setEndIconOnClickListener {
            binding.outlinedTextField.editText?.text?.toString()?.let {
                bookViewModel.handleQuery(it)
            }
        }

        binding.outlinedTextField.editText?.setOnEditorActionListener { textView, i, keyEvent ->
            bookViewModel.handleQuery(textView.text.toString())
            true
        }
        return binding.root
    }

}