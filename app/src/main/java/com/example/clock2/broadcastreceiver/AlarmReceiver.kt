package com.example.clock2.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.clock2.adapter.Constants
import com.example.clock2.service.AlarmService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                startAlarmService(context, intent)
            }

        }
    }

    private fun startAlarmService(context: Context, intent: Intent){
        var intentService = Intent(context, AlarmService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d("Service here"," Happy")
            context.startForegroundService(intentService)
        }
        else{
            Log.d("Service here", " so sad")
            context.startService(intentService)
        }
    }

}