package com.example.whiterabbit

import android.app.Application
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.facebook.stetho.Stetho

class WRApplication : Application() {

    lateinit var requestQueue: RequestQueue

    companion object {
        @Volatile
        private var INSTANCE: WRApplication? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WRApplication().also {
                    INSTANCE = it
                }

            }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    public fun initQue(context: Context) {
        requestQueue = Volley.newRequestQueue(context)
    }
}