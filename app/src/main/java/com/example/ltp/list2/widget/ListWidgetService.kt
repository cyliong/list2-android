package com.example.ltp.list2.widget

import android.R
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class ListWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent)
            = ListRemoteViewsFactory(this.applicationContext, intent)

}

class ListRemoteViewsFactory(
    private val context: Context,
    intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var widgetItems: List<String>

    override fun onCreate() {
        widgetItems = (1..10).map { "Item $it" }
    }

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    override fun getCount() = widgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.simple_list_item_1).apply {
            setTextViewText(R.id.text1,widgetItems[position])
        }
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount() = 1

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

}