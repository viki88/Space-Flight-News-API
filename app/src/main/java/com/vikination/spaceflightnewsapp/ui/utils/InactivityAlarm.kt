package com.vikination.spaceflightnewsapp.ui.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InactivityAlarm :BroadcastReceiver() {

    @Inject lateinit var userPrefs: UserPrefs
    @Inject lateinit var authManager: AuthManager

    override fun onReceive(context: Context, p1: Intent?) {
        Log.i("TAG", "onReceive: ${userPrefs.hasSessionExpired()}")
        if (userPrefs.hasSessionExpired()){
            NotificationHelper(context).showSessionExpiredNotification()
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleInactivityNotification(context: Context, lastActiveTime: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, InactivityAlarm::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val triggerTime = lastActiveTime + (Constants.TIMER_INTERVAL_IN_MINUTES * 60 * 1000) // 10 minutes from last interaction
    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
}