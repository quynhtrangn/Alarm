package com.example.clock2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clock2.R
import com.example.clock2.databinding.ActivityRingBinding
import com.example.clock2.service.AlarmService

class RingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityRingDismiss.setOnClickListener {
            val intent = Intent(this, AlarmService::class.java)
            this.stopService(intent)
            finish()
        }
    }
}