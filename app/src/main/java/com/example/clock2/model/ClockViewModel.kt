package com.example.clock2.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClockViewModel (application: Application) : AndroidViewModel(application){
    private val clockDao = ClockDatabase.getDatabase(application).clockDao()
    private val repository : ClockRepository = ClockRepository(clockDao)

    val getAllData: LiveData<List<ClockData>> = repository.getAllData

    fun insertData(clockData: ClockData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(clockData)
        }
    }

    fun updateData(clockData: ClockData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(clockData)
        }
    }

    fun deleteData(clockData: ClockData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(clockData)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

}