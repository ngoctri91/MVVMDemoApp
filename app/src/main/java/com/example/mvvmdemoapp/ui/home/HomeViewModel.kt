package com.example.mvvmdemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.engine.common.lazyDeferred
import com.example.repository.repositories.PlayListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PlayListRepository) : ViewModel(){
    val playlistResultLiveData by lazyDeferred {
        repository.getPlayList()
    }

    fun prepareData() = viewModelScope.launch (Dispatchers.IO){
        playlistResultLiveData.await()
    }
}