package com.example.ltp.list2.extension

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.ltp.list2.widget.ListWidget

fun Fragment.reloadWidget() {
    val context = requireActivity()
    val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
        component = ComponentName(context, ListWidget::class.java)
    }
    context.sendBroadcast(intent)
}