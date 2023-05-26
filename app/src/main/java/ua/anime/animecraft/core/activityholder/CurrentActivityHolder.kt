package ua.anime.animecraft.core.activityholder

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

object CurrentActivityHolder : Application.ActivityLifecycleCallbacks {

    private var currentActivity = WeakReference<Activity>(null)

    fun register(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun getCurrentActivity(): Activity? = currentActivity.get()

    override fun onActivityResumed(activity: Activity) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
    override fun onActivityDestroyed(activity: Activity) = Unit
}