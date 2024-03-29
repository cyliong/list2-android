package com.example.ltp.list2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.ltp.list2.db.AppDatabase
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.repository.ListRepository
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository

    var isNew = true
    var currentItem by mutableStateOf(ListItem(title = ""))
    var titleFieldValue by mutableStateOf(TextFieldValue("", TextRange.Zero))

    init {
        val listItemDao = AppDatabase.getInstance(application).listItemDao()
        repository = ListRepository.getInstance(listItemDao)
    }

    fun getItem(id: Int) = liveData {
        emit(repository.loadItem(id))
    }

    fun insert(item: ListItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ListItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun onItemChange(item: ListItem) {
        currentItem = item
    }

    fun onTitleFieldValueChange(titleFieldValue: TextFieldValue) {
        this.titleFieldValue = titleFieldValue
    }

}