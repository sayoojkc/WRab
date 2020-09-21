package com.example.whiterabbit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.whiterabbit.database.EmployeeEntity
import com.example.whiterabbit.database.WhiteRabbitDb
import com.example.whiterabbit.network.NetworkHelper


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var searchText: String = ""

    fun setSearchText(searchText : String){
        this.searchText = searchText
    }

    fun getSearchText() : String{
        return searchText
    }

    fun fetchEmployeesFromServer(): LiveData<ArrayList<EmployeeEntity>> {
        return NetworkHelper.getData()
    }

    fun saveDataInDB(arrayList: ArrayList<EmployeeEntity>) {
        Thread {
            WhiteRabbitDb.getDatabase(getApplication())!!.employeeDao().insertAll(arrayList)
        }.start()
    }

    fun getEmployeeList(): LiveData<List<EmployeeEntity>> {
        return WhiteRabbitDb.getDatabase(getApplication())!!.employeeDao().getAll()
    }


    fun getCount(): LiveData<Int> {
        return WhiteRabbitDb.getDatabase(getApplication())!!.employeeDao().getRowCount()
    }

}