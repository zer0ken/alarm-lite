package com.example.alarmapp.addalarm.alarmsound

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context,"알람이 울립니다.",Toast.LENGTH_LONG).show()

        // 알람 소리 재생
        val alarmSoundUri = intent?.getStringExtra("ALARM_SOUND_URI")?.let { Uri.parse(it) }
        val ringtone = RingtoneManager.getRingtone(context, alarmSoundUri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        ringtone.play()

        // 진동 시작
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationPattern = intent?.getStringExtra("VIBRATION_PATTERN")

        // 진동 패턴에 따라 진동 실행
        when (vibrationPattern) {
            "Short" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 50, 100), -1))
            "Medium" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200), -1))
            "Basic Call" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 150, 300), -1))
            else -> vibrator.vibrate(2000) // 기본 2초 진동
        }
    }
}