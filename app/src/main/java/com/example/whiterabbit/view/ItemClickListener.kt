package com.example.whiterabbit.view

import com.example.whiterabbit.database.EmployeeEntity

interface ItemClickListener {
    fun onClick(clickedEmployee : EmployeeEntity)
}