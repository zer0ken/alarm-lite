package com.example.alarmapp.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.time.LocalDateTime
import java.time.ZoneId

interface AlarmScheduler {
    fun schedule(alarm: AlarmState)
    fun cancel(alarm: AlarmState)
}

class MainAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: AlarmState) {
        // to test
        var targetTime = LocalDateTime.now().plusSeconds(5)

        // to release1
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("NAME", alarm.name)
            putExtra("ID", alarm.id)
        }
        val pendingIntent = createPendingIntent(intent, alarm)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
    }

    override fun cancel(alarm: AlarmState) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = createPendingIntent(intent, alarm)
        alarmManager.cancel(pendingIntent)
    }

    private fun createPendingIntent(intent: Intent, alarm: AlarmState) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context,
                alarm.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getBroadcast(
                context,
                alarm.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
}