package com.example.clock2.service

import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.clock2.R
import com.example.clock2.activity.MainActivity
import com.example.clock2.notifications.AppNotification
import java.util.*
import kotlin.math.roundToInt

class TimerService : Service()
{
    // Cung cấp 1 giao diện để tương tác service ko ràng buộc thì để là null
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()
    private var timetmp = 0.0
    // bắt buộc phải override lại hàm hàm chạy trong background vô thời hạn
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        var time = intent.getDoubleExtra(TIME_EXTRA, 0.0)

        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_STICKY
    }

    override fun onDestroy()
    {
        timer.cancel()
        super.onDestroy()
    }
    private inner class TimeTask(private var time: Double) : TimerTask()
    {

        override fun run()
        {
            val intent = Intent(TIMER_UPDATED)
            time++
            sendNotification(time)
            timetmp = time
            Log.d("time count up",time.toString())
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }

    }
    companion object
    {
        const val TIMER_UPDATED = "timerUpdated timer"
        const val TIME_EXTRA = "timeExtra timer"
    }

    private fun sendNotification(time:Double){
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
        }
        val notification = NotificationCompat.Builder(this, AppNotification.CHANNEL_ID_TIMER)
            .setContentTitle("Alarm")
            .setContentText(getTimeStringFromDouble(time))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
            .build()
        startForeground(2,notification)
    }
    private fun getTimeStringFromDouble(time: Double): String{
        val resultInt = time.roundToInt()
        val hours = resultInt %86400 /3600
        val minutes = resultInt %86400 %3600/60
        val seconds = resultInt %86400 %3600%60
        return makeTimeString(hours,minutes,seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =String.format("%02d:%02d:%02d",hours,minutes,seconds)


}