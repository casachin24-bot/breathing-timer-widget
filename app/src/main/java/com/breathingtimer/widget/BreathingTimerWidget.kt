package com.breathingtimer.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class BreathingTimerWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        when (intent?.action) {
            "com.breathingtimer.widget.START" -> {
                context?.startService(Intent(context, BreathingTimerService::class.java))
            }
            "com.breathingtimer.widget.STOP" -> {
                context?.stopService(Intent(context, BreathingTimerService::class.java))
            }
            "com.breathingtimer.widget.UPDATE" -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val componentName = ComponentName(context!!, BreathingTimerWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
        }
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.breathing_timer_widget)

            val startIntent = Intent(context, BreathingTimerWidget::class.java)
            startIntent.action = "com.breathingtimer.widget.START"
            val startPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.btn_start, startPendingIntent)

            val stopIntent = Intent(context, BreathingTimerWidget::class.java)
            stopIntent.action = "com.breathingtimer.widget.STOP"
            val stopPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.btn_stop, stopPendingIntent)

            val state = BreathingTimerState.getState(context)
            views.setTextViewText(R.id.tv_phase, state.currentPhase)
            views.setTextViewText(R.id.tv_timer, state.timeRemaining.toString())

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
