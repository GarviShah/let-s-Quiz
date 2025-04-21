package com.example.myquizapp.core

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


/**
 *
 * Flurry Firebase push notification , Firebase Analytics and
 */
@HiltAndroidApp
class AppController : Application() {


    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this

    }

}
