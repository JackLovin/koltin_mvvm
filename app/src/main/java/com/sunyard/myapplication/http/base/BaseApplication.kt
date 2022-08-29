package com.sunyard.myapplication.http.base

import android.app.Application

/**
 * Created by csj on 2022/8/23.
 */
 open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
       
    }


}