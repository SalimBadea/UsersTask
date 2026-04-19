package com.aait.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
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
