package com.example.clock2.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.clock2.adapter.Constants
import com.example.clock2.service.AlarmService
import com.example.clock2.service.CountService

class CountReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                startCountService(context, intent)
            }
        }
    }

    private fun startCountService(context: Context, intent: Intent){
        var intentService = Intent(context, CountService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentService)
        }
        else{
            context.startService(intentService)
        }
    }

}