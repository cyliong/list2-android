package com.example.ltp.list2.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListItemDao {

    @Query("SELECT * FROM list_item")
    fun getItemsLiveData(): LiveData<List<ListItem>>

    @Query("SELECT * FROM list_item")
    fun getItems(): List<ListItem>

    @Query("SELECT * FROM list_item WHERE id=(:id)")
    suspend fun loadItem(id: Int): ListItem?

    @Insert
    suspend fun insert(item: ListItem)

    @Update
    suspend fun update(item: ListItem)

    @Delete
    suspend fun delete(item: ListItem)

}