package ua.anime.animecraft

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber
import ua.anime.animecraft.core.activityholder.CurrentActivityHolder
import ua.anime.animecraft.core.log.ReportingTree
import ua.anime.animecraft.worker.SkinsWorkFactory
import ua.anime.animecraft.worker.SkinsWorkManager

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
        startSkinWork()
    }

    private fun startSkinWork() {
        WorkManager.initialize(this, getWorkManagerConfiguration())
        WorkManager.getInstance(this).enqueue(SkinsWorkManager.startSkinsWorker())
    }

    private fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(skinsWorkFactory)
        .build()

    private fun createSkinsNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SkinsWorkManager.WORK_SKINS_CHANNEL_ID,
                getString(R.string.work_skins_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReportingTree())
        }
    }
}
