package com.example.clock2.model

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel: ViewModel() {
    private lateinit var countDownTimer: CountDownTimer
    var timeLeft = MutableLiveData<String>()
    var timeOut = MutableLiveData<Boolean>()
    var timer = 0L
    var timerLength = 0L
    fun startTimer(){
        timeOut.postValue(false)
        countDownTimer = object : CountDownTimer(timer*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished/1000
                updateView()
            }
            override fun onFinish() {
                timeOut.postValue(true)
                finishCount()
            }

        }.start()
    }
    fun setTimeProgress(sec:Int, min:Int,hour:Int) : Long{
        return (hour*3600 + min*60 + sec).toLong()
    }

    fun setNewTime(sec:Int, min:Int,hour:Int){
        timer = (hour*3600 + min*60 + sec).toLong()
        timerLength = timer

    }
    private fun updateView(){
        val hour = timer/3600
        val minute = timer/60%60
        val second = timer%60
        timeLeft.postValue(String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second))
    }
    fun finishCount(){
        countDownTimer.cancel()
    }

}