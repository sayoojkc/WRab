package com.example.whiterabbit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class WhiteRabbitDb : RoomDatabase() {
    companion object {
        private var INSTANCE: WhiteRabbitDb? = null
        fun getDatabase(context: Context): WhiteRabbitDb? {
            if (INSTANCE == null) {
                synchronized(WhiteRabbitDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WhiteRabbitDb::class.java, "whiterabbit.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun employeeDao(): EmployeeDao
}