package com.example.alarmapp.addalarm.alarmsound

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Vibrator
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context,"알람이 울립니다.",Toast.LENGTH_LONG).show()

        // 알람 소리 재생
        val ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        ringtone.play()

        // 진동 시작
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(2000) // 2초 동안 진동
    }
}