package com.example.ltp.list2.widget

import android.R.id.text1
import android.R.layout.simple_list_item_1
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.ltp.list2.db.AppDatabase
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.extension.isDarkTheme
import com.example.ltp.list2.repository.ListRepository

class ListWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent) =
        ListRemoteViewsFactory(this.applicationContext, intent)

}

class ListRemoteViewsFactory(
    private val context: Context,
    intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var items: List<ListItem>

    private val repository: ListRepository

    init {
        val listItemDao = AppDatabase.getInstance(context).listItemDao()
        repository = ListRepository.getInstance(listItemDao)
    }

    override fun onCreate() {}

    override fun onDataSetChanged() {
        items = repository.items
    }

    override fun onDestroy() {}

    override fun getCount() = items.size

    override fun getViewAt(position: Int) =
        RemoteViews(context.packageName, simple_list_item_1).apply {
            setTextViewText(text1, items[position].title)
            setTextColor(text1, if (context.isDarkTheme) Color.WHITE else Color.BLACK)

            val fillInIntent = Intent().apply {
                Bundle().also { extras ->
                    extras.putInt(EXTRA_ITEM_ID, items[position].id)
                    putExtras(extras)
                }
            }
            setOnClickFillInIntent(text1, fillInIntent)
        }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount() = 1

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

}