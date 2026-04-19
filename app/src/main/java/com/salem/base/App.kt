package com.salem.base

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private lateinit var notificationManager: NotificationManager

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        notificationManager =
            base.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

}
