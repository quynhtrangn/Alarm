package com.example.clock2.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clock2.databinding.FragmentClockItemBinding
import com.example.clock2.model.ClockData

class ClockAdapter (private val listener: OnItemClickListener) : RecyclerView.Adapter<ClockAdapter.MyViewHolder>() {

    var clockList = emptyList<ClockData>()

    inner class MyViewHolder(val binding: FragmentClockItemBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        fun bind(clockData: ClockData){
            binding.chose.setChecked(clockData.active)
            binding.clockData = clockData
        }

        init {
            binding.chose.setOnClickListener(this)
            binding.txtMinute.setOnClickListener {
                listener.onViewClick(adapterPosition)
            }
            binding.txtTime.setOnClickListener {
                listener.onViewClick(adapterPosition)
            }

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, binding.chose.isChecked)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, OnSwith: Boolean)
        fun onViewClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FragmentClockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = clockList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return clockList.size
    }

    fun setData(clockData: List<ClockData>) {
        val clockDiffUtil = ClockDiffUtil(clockList, clockData)
        val clockDiffResult = DiffUtil.calculateDiff(clockDiffUtil)
        this.clockList = clockData
        clockDiffResult.dispatchUpdatesTo(this)
    }

}


