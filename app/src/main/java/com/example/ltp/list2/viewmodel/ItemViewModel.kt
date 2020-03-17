package com.example.ltp.list2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ltp.list2.db.AppDatabase
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.repository.ListRepository
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository

    val item = MutableLiveData<ListItem?>()

    init {
        val listItemDao = AppDatabase.getInstance(application).listItemDao()
        repository = ListRepository.getInstance(listItemDao)
    }

    fun loadItem(id: Int) = viewModelScope.launch {
        item.value = repository.loadItem(id)
    }

    fun insert(item: ListItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ListItem) = viewModelScope.launch {
        repository.update(item)
    }

}