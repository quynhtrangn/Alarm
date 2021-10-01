package com.example.clock2.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ClockDAO {
    @Query("SELECT * FROM clock_time ORDER BY id ASC")
    fun getAllData(): LiveData<List<ClockData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(clockData: ClockData)

    @Update
    suspend fun updateData(clockData: ClockData)

    @Delete
    suspend fun deleteData(clockData: ClockData)

    @Query("DELETE FROM clock_time")
    suspend fun deleteAllData()
}