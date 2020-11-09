package com.example.ltp.list2.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.ltp.list2.R
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
        RemoteViews(context.packageName, R.layout.widget_item).apply {
            setTextViewText(R.id.text_view_item, items[position].title)
            setTextColor(R.id.text_view_item, if (context.isDarkTheme) Color.WHITE else Color.BLACK)

            val fillInIntent = Intent().apply {
                putExtras(bundleOf(EXTRA_ITEM_ID to items[position].id))
            }
            setOnClickFillInIntent(R.id.text_view_item, fillInIntent)
        }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount() = 1

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

}