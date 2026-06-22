package com.breathingtimer.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import kotlin.concurrent.thread

class BreathingTimerService : Service() {
    private var isRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            startBreathingCycle()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startBreathingCycle() {
        thread {
            val phases = listOf(
                Pair("Inhale", 4),
                Pair("Hold", 7),
                Pair("Exhale", 8)
            )

            while (isRunning) {
                for ((phaseName, duration) in phases) {
                    for (i in duration downTo 1) {
                        if (!isRunning) break
                        updateWidget(phaseName, i)
                        Thread.sleep(1000)
                    }
                }
            }
        }
    }

    private fun updateWidget(phase: String, timeRemaining: Int) {
        val state = BreathingTimerState(phase, timeRemaining)
        BreathingTimerState.setState(this, state)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val componentName = ComponentName(this, BreathingTimerWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        for (appWidgetId in appWidgetIds) {
            BreathingTimerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
        }
    }
}
