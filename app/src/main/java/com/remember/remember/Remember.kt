package com.remember.remember

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.threetenabp.AndroidThreeTen
import com.remember.common.initializer.KoinModules
import com.remember.repository.sharedpreferences.setupSharedPreferences
import com.remember.remember.ui.push.PushCategory
import com.remember.repository.module.networkModule
import com.remember.repository.module.repositoryModule
import com.remember.repository.push.InformationDevice
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Remember : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        setupSharedPreferences()
        setupKoin()
        setupNotificationChannels()
        setupDeviceInformation()
    }

    private fun setupDeviceInformation() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            InformationDevice.setupInformationDevice(
                task.result,
                contentResolver,
                Build.VERSION.SDK_INT.toString()
            )
        })
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@Remember)
            modules(listOf(networkModule, repositoryModule) + KoinModules.modules)
        }
    }

    private fun setupNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            PushCategory.values().forEach {
                val name = getString(it.categoryName)
                val descriptionText = getString(it.description)
                val importance = it.importance
                val channel = NotificationChannel(it.name, name, importance)
                    .apply {
                        description = descriptionText
                    }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
