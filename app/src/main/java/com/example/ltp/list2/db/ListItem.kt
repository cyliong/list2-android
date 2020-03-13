package com.example.ltp.list2.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_item")
data class ListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String
)