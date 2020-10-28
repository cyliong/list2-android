package com.example.ltp.list2.repository

import androidx.lifecycle.LiveData
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.db.ListItemDao

class ListRepository(private val listItemDao: ListItemDao) {

    val itemsLiveData: LiveData<List<ListItem>> = listItemDao.getItemsLiveData()

    suspend fun loadItem(id: Int) = listItemDao.loadItem(id)

    suspend fun insert(item: ListItem) = listItemDao.insert(item)

    suspend fun update(item: ListItem) = listItemDao.update(item)

    suspend fun delete(item: ListItem) = listItemDao.delete(item)

    companion object {

        @Volatile
        private var instance: ListRepository? = null

        fun getInstance(listItemDao: ListItemDao) =
            instance ?: synchronized(this) {
                instance ?: ListRepository(listItemDao).also { instance = it }
            }

    }

}