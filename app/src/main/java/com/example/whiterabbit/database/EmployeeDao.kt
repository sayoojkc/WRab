package com.example.whiterabbit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): LiveData<List<EmployeeEntity>>

    @Insert
    fun insertAll(employeeList: List<EmployeeEntity>)

    @Query("SELECT COUNT() FROM employee")
    fun getRowCount(): LiveData<Int>

}