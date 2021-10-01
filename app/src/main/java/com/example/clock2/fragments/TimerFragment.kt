package com.example.clock2.fragments

import android.app.ActivityManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import com.example.clock2.R
import com.example.clock2.activity.RingCountActivity
import com.example.clock2.databinding.FragmentTimerBinding
import com.example.clock2.notifications.AppNotification
import com.example.clock2.service.TimerService
import kotlin.math.roundToInt


class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(context, TimerService::class.java)
        binding = FragmentTimerBinding.inflate(inflater, container, false)

        binding.startStopButton.setOnClickListener { startStopTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }
        requireActivity().registerReceiver(updateTimer, IntentFilter(TimerService.TIMER_UPDATED))
        return binding.root
    }
    private fun resetTimer()
    {
        stopTimer()
        time = 0.0
        binding.TimeTV.setText(getTimeStringFromDouble(time))
    }
    private fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer()
    {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        activity?.startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.setIconResource(R.drawable.ic_pause)
        timerStarted = true
    }

    private fun stopTimer()
    {
        activity?.stopService(serviceIntent)
        binding.startStopButton.text = "Start"
        binding.startStopButton.setIconResource(R.drawable.ic_play)
        timerStarted = false
    }

    private val updateTimer:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA,0.0)
            if(time!=0.0 ){
                binding.startStopButton.text = "Stop"
                binding.startStopButton.setIconResource(R.drawable.ic_pause)
                timerStarted = true
            }
            binding.TimeTV.text = getTimeStringFromDouble(time)


        }

    }
    private fun getTimeStringFromDouble(time: Double): String{
        val resultInt = time.roundToInt()
        val hours = resultInt %86400 /3600
        val minutes = resultInt %86400 %3600/60
        val seconds = resultInt %86400 %3600%60
        return makeTimeString(hours,minutes,seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String = String.format("%02d:%02d:%02d",hours,minutes,seconds)
}