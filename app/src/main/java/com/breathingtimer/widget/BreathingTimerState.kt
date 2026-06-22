package com.breathingtimer.widget

import android.content.Context
import android.content.SharedPreferences

data class BreathingTimerState(
    val currentPhase: String = "Ready",
    val timeRemaining: Int = 0
) {
    companion object {
        private const val PREFS_NAME = "breathing_timer_prefs"
        private const val KEY_PHASE = "current_phase"
        private const val KEY_TIME = "time_remaining"

        fun setState(context: Context, state: BreathingTimerState) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().apply {
                putString(KEY_PHASE, state.currentPhase)
                putInt(KEY_TIME, state.timeRemaining)
                apply()
            }
        }

        fun getState(context: Context): BreathingTimerState {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val phase = prefs.getString(KEY_PHASE, "Ready") ?: "Ready"
            val time = prefs.getInt(KEY_TIME, 0)
            return BreathingTimerState(phase, time)
        }
    }
}
