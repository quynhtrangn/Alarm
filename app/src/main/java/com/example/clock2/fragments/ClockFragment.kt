package com.example.clock2.fragments

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.clock2.adapter.ClockAdapter
import com.example.clock2.adapter.RandomUtil
import com.example.clock2.databinding.FragmentClockBinding
import com.example.clock2.broadcastreceiver.AlarmReceiver
import com.example.clock2.model.ClockData
import com.example.clock2.model.ClockViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*
import kotlin.math.roundToInt


class ClockFragment : Fragment(), ClockAdapter.OnItemClickListener{

    private lateinit var binding: FragmentClockBinding
    private val clockViewModel: ClockViewModel by viewModels<ClockViewModel>()
    private val adapter: ClockAdapter by lazy { ClockAdapter(this) }
    lateinit var alarm: SetAlarm


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClockBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        alarm = SetAlarm(context!!)
        var gio = ""
        var phut = ""
        binding.floatingActionButton.setOnClickListener{
            val cal = Calendar.getInstance()
            Log.d("Time in Milis1 ", cal.timeInMillis.toString() )
            val time = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                gio= cal.get(Calendar.HOUR_OF_DAY).toString()
                phut = cal.get(Calendar.MINUTE).toString()
                if (cal.get(Calendar.HOUR_OF_DAY)<10){
                    gio= "0"+cal.get(Calendar.HOUR_OF_DAY).toString()
                }
                if(cal.get(Calendar.MINUTE)<10){
                    phut = "0"+cal.get(Calendar.MINUTE).toString()
                }
                val clockData = ClockData(0, "", false, gio, phut, cal.timeInMillis,RandomUtil.getRandomInt())
                Log.d("Time in Milis2 ", cal.timeInMillis.toString() )
                clockViewModel.insertData(clockData)
                Toast.makeText(requireContext(), "Th??m b??o th???c th??nh c??ng!", Toast.LENGTH_LONG).show()

            }
            TimePickerDialog(context, time, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        setUpRecyclerView()
//      C???p nh???t th?????ng xuy??n view model
        clockViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            adapter.setData(data)
        })

        return binding.root
    }

    private fun setUpRecyclerView(){
        val recyclerview = binding.recyclerView
        recyclerview.adapter = adapter
        recyclerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        swipeToDelete(recyclerview)

    }


    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.clockList[viewHolder.adapterPosition]
                val builder = AlertDialog.Builder(context)
                builder.setPositiveButton("C??"){  _, _ ->
                    Toast.makeText(context, "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show()
                    clockViewModel.deleteData(deletedItem)
                    //adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
                builder.setNegativeButton("Kh??ng") { _, _ ->
                    adapter.notifyDataSetChanged()
                }
                builder.setTitle("B???n c?? ch???c ch???n mu???n x??a kh??ng?")
                builder.create().show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    override fun onItemClick(position: Int, OnSwith: Boolean) {
        val clockData = adapter.clockList[position]
        clockData.active = OnSwith
        Log.e("switch", clockData.active.toString())
        clockViewModel.updateData(clockData)
        alarm.setExactAlarm(clockData.clock_timeInMillis, clockData)
    }

    override fun onViewClick(position: Int) {
        var clockData = adapter.clockList[position]
        val cal = Calendar.getInstance()
        val time = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            clockData.minute = cal.get(Calendar.MINUTE).toString()
            clockData.hour = cal.get(Calendar.HOUR_OF_DAY).toString()
            clockViewModel.updateData(clockData)
            adapter.notifyDataSetChanged()
            Toast.makeText(context, "C???p nh???t th??nh c??ng!", Toast.LENGTH_SHORT).show()
        }
        TimePickerDialog(context, time, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

}