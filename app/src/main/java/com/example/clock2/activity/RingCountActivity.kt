package com.example.clock2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.clock2.R
import com.example.clock2.databinding.ActivityRingBinding
import com.example.clock2.databinding.ActivityRingCountBinding
import com.example.clock2.fragments.CountFragment
import com.example.clock2.service.AlarmService
import com.example.clock2.service.CountService


class RingCountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRingCountBinding
    lateinit var intentMain: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingCountBinding.inflate(layoutInflater)
        intentMain = Intent(this, MainActivity::class.java)
        setContentView(binding.root)
        binding.btnDismiss.setOnClickListener {
            val intent = Intent(this, CountService::class.java)
            this.stopService(intent)
            startActivity(intentMain)
//            findNavController().navigate(R.id.move_count)
            finish()
        }
    }
}