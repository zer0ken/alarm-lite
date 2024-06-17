package com.example.alarmapp.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

interface AlarmScheduler {
    fun schedule(alarm: AlarmState): Boolean
    fun cancel(alarm: AlarmState)
}

class MainAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: AlarmState): Boolean {
        // to test
//        var targetTime = LocalDateTime.now().plusSeconds(5)

        // to release
        val targetTime = alarm.getNextRingTime()
        Log.d(
            "@Scheduler",
            "next ring for alarm ${alarm.id} is $targetTime, ${targetTime.dayOfWeek.name}"
        )

        val expireDate = alarm.expireDate?.let {
            LocalDateTime.ofEpochSecond(alarm.expireDate!! / 1000, 0, ZoneOffset.UTC)
                .plusDays(1)
        }
        Log.d("@Scheduler", "expire date of alarm ${alarm.id} is $expireDate")
        if (expireDate != null && targetTime.isAfter(expireDate)) {
            Log.d("@Scheduler", "alarm ${alarm.id} is expired")
            return false
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ID", alarm.id)
        }
        val pendingIntent = createPendingIntent(intent, alarm)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
        return true
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