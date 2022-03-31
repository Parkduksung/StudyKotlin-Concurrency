package com.example.red_chapter1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _mainViewStateLiveData = MutableLiveData<MainViewState>()
    val mainViewStateLiveData: LiveData<MainViewState> = _mainViewStateLiveData

    init {
        showExam1Result()
    }

    private fun showExam1Result() {
        CoroutineScope(Dispatchers.Main).launch {
            launch {
                viewStateChanged(MainViewState.GetData("3"))
                delay(1000L)
            }

            launch {
                delay(2000L)
                viewStateChanged(MainViewState.GetData("2"))
            }
            delay(1000L)
            viewStateChanged(MainViewState.GetData("1"))
        }
    }

    private fun viewStateChanged(mainViewState: MainViewState) {
        _mainViewStateLiveData.value = mainViewState
    }


    sealed class MainViewState {
        data class GetData(val data: String) : MainViewState()
    }
}