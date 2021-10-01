package com.example.clock2.fragments

import android.app.ActivityManager
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock2.databinding.FragmentCountBinding
import com.example.clock2.service.CountService
import kotlin.math.roundToInt
class CountFragment : Fragment(){

    private lateinit var binding: FragmentCountBinding
    lateinit var serviceCount: Intent
    private var isActive = false
    var timeSum = 0.0
    var hour = 0.0
    var minute = 0.0
    var second = 0.0
//    var ok = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentCountBinding.inflate(inflater, container, false)
        serviceCount = Intent(context, CountService::class.java)

        binding.pickHour.maxValue=23
        binding.pickHour.minValue = 0

        binding.pickMinute.maxValue=59
        binding.pickMinute.minValue = 0

        binding.pickSecond.maxValue=59
        binding.pickSecond.minValue = 0


        binding.pickHour.setOnValueChangedListener{ _, _, i2->
            hour = i2.toDouble()
            binding.txtTime.text = (hour*60*60 + minute*60 +second).toString()
            timeSum = binding.txtTime.text.toString().toDouble() //getTimeDoubleFromString(binding.txtTime.text)
            Log.d("Hour ",hour.toString() )
            binding.btnStartStop.isEnabled = true
        }

        binding.pickMinute.setOnValueChangedListener{ _, _, i2->
            minute = i2.toDouble()
            binding.txtTime.text = (hour*60*60 + minute*60 +second).toString()//String.format("%02f:%02f:%02f",hour,minute,second)
            timeSum = binding.txtTime.text.toString().toDouble()
            Log.d("Minute ", minute.toString() )
            binding.btnStartStop.isEnabled = true
        }
        binding.pickSecond.setOnValueChangedListener{ _, _, i2->
            second = i2.toDouble()
            binding.txtTime.text = (hour*60*60 + minute*60 +second).toString()
            timeSum = binding.txtTime.text.toString().toDouble()
            Log.d("second ",second.toString() )
            binding.btnStartStop.isEnabled = true
        }

        binding.btnStartStop.setOnClickListener {
            binding.pickHour.visibility = View.GONE
            binding.pickMinute.visibility = View.GONE
            binding.pickSecond.visibility = View.GONE
            startStopCount()

        }
        binding.btnDelete.setOnClickListener {
            resetCount()

        }
//        timeSum = hour*60*60 + minute*60 +second
        requireActivity().registerReceiver(updateTime, IntentFilter(CountService.TIMER_UPDATED))
        return binding.root
    }

    private fun resetCount() {
        binding.pickHour.visibility = View.VISIBLE
        binding.pickMinute.visibility = View.VISIBLE
        binding.pickSecond.visibility = View.VISIBLE

        binding.pickHour.value = 0
        binding.pickMinute.value = 0
        binding.pickSecond.value = 0

        binding.timeLeftLayout.visibility = View.INVISIBLE
        binding.btnStartStop.isEnabled = false
        binding.btnStartStop.text = "Start"
        isActive = false

        hour = 0.0
        minute = 0.0
        second = 0.0
        activity?.stopService(serviceCount)
        timeSum = 0.0
        binding.txtTime.setText(getTimeStringFromDouble(timeSum))
    }
    @Suppress("DEPRECATION") // Deprecated for third party Services.
    fun <T> Context.isServiceRunning(service: Class<T>) =
        (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == service.name }

    private fun startStopCount() {
        if(isActive)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        if(isActive){
            timeSum = binding.txtTime.text.toString().toDouble()
        }
        serviceCount.putExtra(CountService.TIME_EXTRA, timeSum)
        activity?.startService(serviceCount)
        binding.btnStartStop.text = "Stop"
        isActive = true
        isActive = true
        binding.btnDelete.isEnabled = true
    }

    private fun stopTimer() {
        activity?.stopService(serviceCount)
        binding.btnStartStop.text = "Start"
        isActive = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            timeSum = intent.getDoubleExtra(CountService.TIME_EXTRA,0.0)
            if(timeSum!=0.0 ){
                binding.btnStartStop.text = "Stop"
                isActive = true
            }
            binding.timeLeftLayout.visibility = View.VISIBLE
            binding.txtTime.text = getTimeStringFromDouble(timeSum)
        }

    }
//    private fun getTimeDoubleFromString(txtTime: String): Double{
//        var arrTime = txtTime.split(":")
//        var hours = arrTime.get(0).toString().toDouble()
//        var minutes = arrTime.get(1).toString().toDouble()
//        var seconds = arrTime.get(1).toString().toDouble()
//        return makeTimeDouble(hours, minutes,seconds)
//    }
//
//    private fun makeTimeDouble(hours: Double, minutes: Double, seconds: Double): Double = (hours*60*60 + minutes*60 +seconds)
////
    private fun getTimeStringFromDouble(time: Double): String{
        val resultInt = time.roundToInt()
        val hours = resultInt %86400 /3600
        val minutes = resultInt %86400 %3600/60
        val seconds = resultInt %86400 %3600%60
        return makeTimeString(hours,minutes,seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =String.format("%02d:%02d:%02d",hours,minutes,seconds)

//    private fun resetTimer() {
//        binding.btnPause.isEnabled = false
//        binding.btnStop.isEnabled = false
//        binding.btnStart.isEnabled = true
//        binding.btnPause.text = "Pause"
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        serviceCount = Intent(context, CountService::class.java)
//        countViewModel = ViewModelProvider(requireActivity()).get(CountViewModel::class.java)
//        countViewModel.timeLeft.observe(viewLifecycleOwner, {
//            binding.txtTime.text = it
//        })
//        countViewModel.timeOut.observe(viewLifecycleOwner, {
//            if (it) {
//                resetTimer()
//                isActive = false
//            }
//        })
//            TimePickerDialog(
//                context,
//                time,
//                cal.get(Calendar.HOUR_OF_DAY),
//                cal.get(Calendar.MINUTE),
//                true
//            ).show()
//            hour = cal.get(Calendar.HOUR_OF_DAY)
//            minute = cal.get(Calendar.MINUTE)
//            timeSum = hour * 60 * 60.0 + minute * 60.0
//            Log.d("time sum", timeSum.toString())
//            binding.txtTime.text =  getTimeString(timeSum)
//        }
//        binding.btnStart.setOnClickListener {
//            isActive = true
//            binding.btnStart.isEnabled = false
//            binding.btnPause.isEnabled = true
//            binding.btnStop.isEnabled = true
//                countViewModel.setNewTime(0, minute, hour)
//                countViewModel.startTimer()
//                if (ok) {
//                    timeSum = hour * 60 * 60.0 + minute * 60.0
//                    ok = false
//                    serviceCount.putExtra("timeSum", timeSum)
//                } else {
//                    timeSum--
//                    serviceCount.putExtra("timeSum", timeSum)
//                }
//
//            binding.txtTime.text =  getTimeString(timeSum)
//                activity?.startService(serviceCount)
//            }
//            binding.btnPause.setOnClickListener {
//                if (isActive) {
//                    countViewModel.finishCount()
//                    binding.btnPause.text = "Resume"
//                    isActive = false
//                    activity?.stopService(serviceCount)
//                } else {
//                    countViewModel.startTimer()
//                    isActive = true
//                    binding.btnPause.text = "Pause"
//                }
//            }
//            binding.btnStop.setOnClickListener {
//                isActive = false
//                resetTimer()
//                activity?.stopService(serviceCount)
//                countViewModel.finishCount()
//            }
//
//        }
//    private fun getTimeString(time: Double): String{
//        val resultInt = time.roundToInt()
//        val hours = resultInt %86400 /3600
//        val minutes = resultInt %86400 %3600/60
//        val seconds = resultInt %86400 %3600%60
//        return makeTimeString(hours,minutes,seconds)
//    }
//
//    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =String.format("%02d:%02d:%02d",hours,minutes,seconds)

}

