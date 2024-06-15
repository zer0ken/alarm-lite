package com.example.alarmapp.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

interface AlarmScheduler {
    fun schedule(alarm: AlarmState)
    fun cancel(alarm: AlarmState)
}

class MainAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: AlarmState) {
        // to test
//        var targetTime = LocalDateTime.now().plusSeconds(5)

        // to release
        var targetTime = getScheduleTime(alarm)

        Log.d("@Scheduler", "next ring for alarm ${alarm.id} is $targetTime, ${targetTime.dayOfWeek.name}")

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ID", alarm.id)
        }
        val pendingIntent = createPendingIntent(intent, alarm)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
    }

    private fun getScheduleTime(alarmState: AlarmState): LocalDateTime {
        val now = LocalDateTime.now()

        var targetTime = LocalDateTime.now()
            .withHour(alarmState.hour)
            .withMinute(alarmState.minute)
            .withSecond(0)
            .withNano(0)

        if (alarmState.repeatOnWeekdays.any { it }) {
            var minDiff = Duration.ofDays(7)
            var next = targetTime.plusDays(7)

            alarmState.repeatOnWeekdays.forEachIndexed { dayOfWeek, doRepeat ->
            if (doRepeat){
                    val _dayOfWeek = if (dayOfWeek == 0) DayOfWeek.SUNDAY else DayOfWeek.of(dayOfWeek)
                    var _next = targetTime.with(TemporalAdjusters.nextOrSame(_dayOfWeek))
                    if (_next.isBefore(now)) {
                        _next = _next.plusDays(7)
                    }
                    val _minDiff = Duration.between(now, _next)
                    if (_minDiff < minDiff) {
                        next = _next
                        minDiff = _minDiff
                    }
                }
            }
            targetTime = next
        } else if (targetTime.isBefore(now)) {
            targetTime = targetTime.plusDays(1)
        }

        return targetTime
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