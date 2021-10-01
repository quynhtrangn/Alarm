package com.example.clock2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "clock_time")
@Parcelize
data class ClockData(
     @PrimaryKey(autoGenerate = true)
     var  id: Int,
     var date: String,
     var active: Boolean,
     var hour: String,
     var minute: String,
     var clock_timeInMillis: Long,
     var clock_RequestCode: Int
)  : Parcelable