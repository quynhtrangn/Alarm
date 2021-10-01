package com.example.clock2.model

import androidx.lifecycle.LiveData


class ClockRepository(private val clockDAO: ClockDAO) {
    val getAllData: LiveData<List<ClockData>> = clockDAO.getAllData()

    suspend fun insertData(clockData: ClockData){
        clockDAO.insertData(clockData)
    }

    suspend fun updateData(clockData: ClockData){
        clockDAO.updateData(clockData)
    }

    suspend fun deleteData(clockData: ClockData) {
        clockDAO.deleteData(clockData)
    }

    suspend fun deleteAllData() {
        clockDAO.deleteAllData()
    }

}