package ua.anime.animecraft

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ua.anime.animecraft.core.log.ReportingTree


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

@HiltAndroidApp
class AnimeCraftApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReportingTree())
        }
    }
}