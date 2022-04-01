package com.example.red_chapter1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.red_chapter1.Sample.printLog
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel : ViewModel() {

    private val _mainViewStateLiveData = MutableLiveData<MainViewState>()
    val mainViewStateLiveData: LiveData<MainViewState> = _mainViewStateLiveData


    private lateinit var job1: Job

    private lateinit var job2: Job


    init {
        showExam1Result()
    }

    private fun showExam1Result() {
        CoroutineScope(Main).launch {
            viewStateChanged(MainViewState.Loading(isLoading = true)) // 1

            job1 = launch {
                printLog("job1")
                delay(1000L)
                viewStateChanged(MainViewState.Loading(isLoading = false))
                printLog("job2")
            }

            job2 = launch {
                printLog("2")
                delay(1000L)
                printLog("4")
                job1.join()
            }

            launch {
                printLog("3")
                job2.cancel()
            }

            launch {
                job2.join()
                delay(2000L)
                printLog("22")
            }
            delay(1000L)
            printLog("1")
        }
    }

    private fun viewStateChanged(mainViewState: MainViewState) {
        _mainViewStateLiveData.value = mainViewState
    }


    sealed class MainViewState {
        data class GetData(val data: String) : MainViewState()
        data class Loading(val isLoading: Boolean) : MainViewState()
    }
}