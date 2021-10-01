package com.example.clock2.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.clock2.adapter.Constants
import com.example.clock2.adapter.RandomUtil
import com.example.clock2.broadcastreceiver.AlarmReceiver
import com.example.clock2.model.ClockData

import com.example.clock2.service.AlarmService

class SetAlarm(private val context: Context) {
    private val alarmManager : AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun cancelAlarm(reCode: Int){
        val intent = getIntent().apply{ action = Constants.ACTION_SET_EXACT}
        val pendingIntent = PendingIntent.getBroadcast(context, reCode, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setExactAlarm(timeInMillis: Long, clockData: ClockData){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT
                    putExtra(Constants.HOUR, clockData.hour)
                    putExtra(Constants.MINUTE, clockData.minute)
                }, clockData
            )
        )
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setAlarm(timeInMillis: Long,pendingIntent: PendingIntent){
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }
    private fun getPendingIntent(intent: Intent, clockData: ClockData)=
        PendingIntent.getBroadcast(
            context,
            clockData.clock_RequestCode,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

}

