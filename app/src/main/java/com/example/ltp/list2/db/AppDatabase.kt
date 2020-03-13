package com.example.ltp.list2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun listItemDao(): ListItemDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "list_database"
                ).build().also { instance = it }
            }

    }

}