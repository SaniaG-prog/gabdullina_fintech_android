package com.fintech.developerslife

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel: ViewModel() {
    private val apiService: ApiService by lazy { ApiService.create() }

    private val backList: ArrayDeque<ApiResult> = ArrayDeque()
    private val nextList: ArrayDeque<ApiResult> = ArrayDeque()
    val currentGif: MutableLiveData<ApiResult> = MutableLiveData()
    var isBackButtonClickable: MutableLiveData<Boolean> = MutableLiveData()

    fun showNextGif() {
        viewModelScope.launch {
            if (nextList.isEmpty()) {
                nextList.addLast(withContext(Dispatchers.IO) {
                    apiService.getGif()
                })
            }
            withContext(Dispatchers.Main) {
                if (currentGif.value != null) {
                    backList.addLast(currentGif.value!!)
                }
                currentGif.postValue(nextList.first())
                nextList.removeFirst()
                changeBackButtonClickable()
            }
        }
    }

    fun showPrevGif() {
        nextList.addFirst(currentGif.value!!)
        currentGif.postValue(backList.last())
        changeBackButtonClickable()
    }

    fun changeBackButtonClickable() {
        isBackButtonClickable.postValue(!backList.isEmpty())
    }
}