package com.test.kotlinsqliteexample

import android.app.Application

/**
 * Created by admin on 2017/11/21.
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}