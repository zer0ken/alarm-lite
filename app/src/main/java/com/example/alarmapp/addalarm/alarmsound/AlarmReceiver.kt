package com.example.alarmapp.addalarm.alarmsound

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context,"알람이 울립니다.",Toast.LENGTH_LONG).show()

        val alarmSoundUri = intent?.getStringExtra("ALARM_SOUND_URI")?.let { Uri.parse(it) }
        val ringtone = RingtoneManager.getRingtone(context, alarmSoundUri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))

        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationPattern = intent?.getStringExtra("VIBRATION_PATTERN")
        val repeatGap = intent?.getIntExtra("REPEAT_GAP",5) ?: 5
        val repeatNumber = intent?.getIntExtra("REPEAT_NUMBER", 3) ?: 3

        val handler = Handler()
        var repeatCount = 0

        val alarmRunnable = object : Runnable {
            override fun run() {
                if (repeatCount < repeatNumber){
                    ringtone.play()
                    when (vibrationPattern) {
                        "Short" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 50, 100), -1))
                        "Medium" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200), -1))
                        "Basic Call" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 150, 300), -1))
                        else -> vibrator.vibrate(0) // 무음
                    }
                    handler.postDelayed({
                        ringtone.stop()
                    },10000)
                    repeatCount++
                    handler.postDelayed(this, (repeatGap*60000).toLong())
                }
            }
        }
        alarmRunnable.run()
    }
}