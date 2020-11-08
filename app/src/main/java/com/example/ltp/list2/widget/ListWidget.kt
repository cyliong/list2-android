package com.example.ltp.list2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.example.ltp.list2.R
import com.example.ltp.list2.ui.ItemFragmentArgs
import com.example.ltp.list2.ui.MainActivity

const val EXTRA_ITEM_ID = "com.example.ltp.list2.widget.EXTRA_ITEM_ID"
private const val EDIT_ACTION = "com.example.ltp.list2.widget.EDIT_ACTION"

/**
 * Implementation of App Widget functionality.
 */
class ListWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            AppWidgetManager.getInstance(context).apply {
                notifyAppWidgetViewDataChanged(
                    getAppWidgetIds(ComponentName(context, ListWidget::class.java)),
                    R.id.list_view
                )
            }
        } else if (intent.action == EDIT_ACTION) {
            val itemId = intent.getIntExtra(EXTRA_ITEM_ID, 0)

            NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.itemFragment)
                .setArguments(ItemFragmentArgs(itemId).toBundle())
                .createPendingIntent()
                .send()
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
        .let { intent ->
            PendingIntent.getActivity(context, 0, intent, 0)
        }

    val addItemPendingIntent = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.itemFragment)
        .createPendingIntent()

    val intent = Intent(context, ListWidgetService::class.java)

    val pendingIntentTemplate: PendingIntent = Intent(context, ListWidget::class.java)
        .run {
            action = EDIT_ACTION
            PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.list_widget).apply {
        setRemoteAdapter(R.id.list_view, intent)
        setOnClickPendingIntent(R.id.text_view_title, pendingIntent)
        setOnClickPendingIntent(R.id.image_button_add, addItemPendingIntent)
        setPendingIntentTemplate(R.id.list_view, pendingIntentTemplate)

        setEmptyView(R.id.list_view, R.id.empty_view)
        setOnClickPendingIntent(R.id.empty_view, pendingIntent)
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}