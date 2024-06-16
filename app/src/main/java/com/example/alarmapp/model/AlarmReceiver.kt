package com.example.alarmapp.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.alarmapp.R
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}

class AlarmReceiver : BroadcastReceiver() {
    lateinit var repository: Repository
    lateinit var alarmScheduler: MainAlarmScheduler

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) = goAsync {
        repository = Repository(AlarmDatabase.getInstance(context = context))
        alarmScheduler = MainAlarmScheduler(context)

        val alarm = repository.getAlarm(intent.getIntExtra("ID", -1)) ?: return@goAsync
        Log.d("@Receiver", "received alarm ${alarm.id}")

        if (alarm.repeatOnWeekdays.none { it } || !alarmScheduler.schedule(alarm)) {
            Log.d("@Receiver", "alarm ${alarm.id} were turned off")
            alarm.isOn = false
            repository.update(alarm)
        }

        launch {
            notify(context, alarm)
            ring(context, alarm)
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun notify(context: Context, alarm: AlarmState) {
        val pendingIntent = createPendingIntent(context, alarm.id)

        val channelId = "ReceivedAlarmChannel"
        val channelName = "ReceivedAlarmChannel"
        val notificationId = 0

        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)


        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_access_alarm_24)
            .setContentTitle("알람이 울립니다.")
            .setContentText(alarm.name.ifBlank { null })
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun createPendingIntent(context: Context, alarmId: Int): PendingIntent? {
        val intent = Intent(Intent.ACTION_VIEW, "myapp://main_screen".toUri())
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context, alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context, alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        return pendingIntent
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    suspend fun ring(context: Context, alarm: AlarmState): Ringtone {
        val ringtoneUri = alarm.selectedRingtoneUri
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
        ringtone.play()

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 150, 300), -1))

        delay(10000)
        if (ringtone.isPlaying) {
            ringtone.stop()
        }

        return ringtone
    }
}

