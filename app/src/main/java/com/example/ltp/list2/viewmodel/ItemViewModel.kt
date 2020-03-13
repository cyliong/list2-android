package com.example.ltp.list2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltp.list2.db.AppDatabase
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.repository.ListRepository
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository

    var item: ListItem? = null

    init {
        val listItemDao = AppDatabase.getInstance(application).listItemDao()
        repository = ListRepository.getInstance(listItemDao)
    }

    fun getItem(id: Int) = repository.getItem(id)

    fun insert(item: ListItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ListItem) = viewModelScope.launch {
        repository.update(item)
    }

}