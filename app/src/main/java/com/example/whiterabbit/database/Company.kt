package com.example.whiterabbit.database

import androidx.room.Entity

@Entity(tableName = "company")
data class Company (
    var companyName : String =  "",
    var catchPhrase : String = "",
    var bs : String = ""
)