package com.example.ltp.list2.extension

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import com.example.ltp.list2.widget.ListWidget

fun Fragment.reloadWidget() {
    val context = requireActivity()
    val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
        component = ComponentName(context, ListWidget::class.java)
    }
    context.sendBroadcast(intent)
}

val Context.isDarkTheme get() =
    resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES