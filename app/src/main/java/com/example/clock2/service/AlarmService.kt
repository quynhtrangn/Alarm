package com.example.clock2.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.clock2.R
import com.example.clock2.activity.RingActivity
import com.example.clock2.notifications.AppNotification.Companion.CHANNEL_ID_ALARM

class AlarmService : Service(){

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notiIntent = Intent(this, RingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,notiIntent,0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_ALARM)
            .setContentTitle("Alarm")
            .setContentText("get up get up")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        startForeground(1,notification)
        Log.d("alarm service", "nothing here")
        return START_STICKY
    }

}