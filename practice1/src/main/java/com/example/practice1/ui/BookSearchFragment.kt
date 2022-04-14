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

    private val adapter: BookSearchAdapter = BookSearchAdapter {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(binding.containerList.id, BookDetailFragment.newInstance(it))
            .addToBackStack("BookDetailFragment").commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookViewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookSearchBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            bookViewModel.pagingDataFlow.collectLatest {
                adapter.submitData(it)
            }
        }
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel.handleQuery("android")
    }
}