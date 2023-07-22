package com.anime.animecraft

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.work.Configuration
import androidx.work.WorkManager
import com.anime.animecraft.activity.holder.CurrentActivityHolder
import com.anime.animecraft.core.android.extensions.permissionGranted
import com.anime.animecraft.download.manager.SkinsWorkFactory
import com.anime.animecraft.download.manager.SkinsWorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber
import ua.anime.animecraft.BuildConfig
import ua.anime.animecraft.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

@HiltAndroidApp
class AnimeCraftApp : Application() {

    @Inject
    lateinit var skinsWorkFactory: SkinsWorkFactory

    override fun onCreate() {
        super.onCreate()
        CurrentActivityHolder.register(this)
        setupTimber()
        createSkinsNotificationChannel()
        initializeSkinWorkManager()
        startSkinWork()
    }

    private fun initializeSkinWorkManager() {
        WorkManager.initialize(this, getWorkManagerConfiguration())
    }

    fun startSkinWork() {
        if (permissionGranted(WRITE_EXTERNAL_STORAGE) || SDK_INT > Build.VERSION_CODES.Q) {
            WorkManager.getInstance(this).enqueue(SkinsWorkManager.startSkinsWorker())
        }
    }

    private fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(skinsWorkFactory)
        .build()

    private fun createSkinsNotificationChannel() {
        val channel = NotificationChannel(
            SkinsWorkManager.WORK_SKINS_CHANNEL_ID,
            getString(R.string.work_skins_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun setupTimber() {
        val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else ReportingTree()
        Timber.plant(tree)
    }
}
