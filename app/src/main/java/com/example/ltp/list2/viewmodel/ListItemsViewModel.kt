package com.example.ltp.list2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.ltp.list2.db.AppDatabase
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.repository.ListRepository
import kotlinx.coroutines.launch

class ListItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository

    init {
        val listItemDao = AppDatabase.getInstance(application).listItemDao()
        repository = ListRepository.getInstance(listItemDao)
    }

    fun getItems() = liveData {
        emitSource(repository.items)
    }

    fun delete(item: ListItem) =  viewModelScope.launch {
        repository.delete(item)
    }
}