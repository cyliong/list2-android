package com.example.ltp.list2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.ltp.list2.R
import com.example.ltp.list2.ui.MainActivity

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
    val intent = Intent(context, ListWidgetService::class.java)

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.list_widget).apply {
        setRemoteAdapter(R.id.list_view, intent)
        setOnClickPendingIntent(R.id.text_view_title, pendingIntent)
        setPendingIntentTemplate(R.id.list_view, pendingIntent)
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}