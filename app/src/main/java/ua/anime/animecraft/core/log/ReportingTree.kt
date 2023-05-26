package ua.anime.animecraft.core.log

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

class ReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.WARN || priority == Log.ERROR) {
            recordException(priority, tag, message, t)
        }
    }

    private fun recordException(priority: Int, tag: String?, message: String, t: Throwable?) =
        FirebaseCrashlytics.getInstance().run {
            setCustomKey("priority", priority)
            setCustomKey("tag", tag ?: "")
            setCustomKey("message", message)
            log("E/TAG: $message")
            recordException(t ?: Exception(message))
        }
}
