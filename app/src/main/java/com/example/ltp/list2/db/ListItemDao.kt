package com.example.ltp.list2.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListItemDao {

    @Query("SELECT * FROM list_item")
    fun getAll(): LiveData<List<ListItem>>

    @Query("SELECT * FROM list_item WHERE id=(:id)")
    fun getItem(id: Int): LiveData<ListItem>

    @Insert
    suspend fun insert(item: ListItem)

    @Update
    suspend fun update(item: ListItem)

    @Delete
    suspend fun delete(item: ListItem)

}