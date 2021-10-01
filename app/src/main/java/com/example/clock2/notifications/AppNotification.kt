package com.example.clock2.notifications
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

class AppNotification : Application() {
    companion object{
        const val CHANNEL_ID_ALARM = "ALARM CHANNEL"
        const val CHANNEL_ID_TIMER = "TIMER CHANNEL"
        const val CHANNEL_ID_COUNT = "COUNT CHANNEL"
        const val CHANNEL_ID_DEFAULT = "DEFAULT CHANNEL"
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() { // the notification on Android 8.0 and higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val attribute = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val uriCount = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val attributeCount = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val uri4 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val attribute4 = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel1 = NotificationChannel(CHANNEL_ID_ALARM, "Alarm Channel", NotificationManager.IMPORTANCE_DEFAULT)
            channel1.setSound(uri1,attribute)
            val notificationManager1: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager1?.createNotificationChannel(channel1)

            val channel2 = NotificationChannel(CHANNEL_ID_TIMER, "TIMER Channel", NotificationManager.IMPORTANCE_LOW)
            channel2.setSound(uri1,attribute)
            val notificationManager2: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager2?.createNotificationChannel(channel2)

            val channel3 = NotificationChannel(CHANNEL_ID_COUNT, "COUNT Channel", NotificationManager.IMPORTANCE_HIGH)
            channel3.setSound(uriCount,attributeCount)
            val notificationManager3: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager3?.createNotificationChannel(channel3)

            val channel4 = NotificationChannel(CHANNEL_ID_DEFAULT, "DEFAULT Channel", NotificationManager.IMPORTANCE_LOW)
            channel4.setSound(uri4,attribute4)
            val notificationManager4: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager4?.createNotificationChannel(channel4)
        }
    }
}
