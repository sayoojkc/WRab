package com.example.whiterabbit.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "employee")
data class EmployeeEntity(
    @NotNull @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "profileImage") var profileImage: String?,
    @ColumnInfo(name = "phone") var phone: String?,
    @ColumnInfo(name = "website") var website: String?,
    @Embedded var company: Company,
    @Embedded var address: Address

)