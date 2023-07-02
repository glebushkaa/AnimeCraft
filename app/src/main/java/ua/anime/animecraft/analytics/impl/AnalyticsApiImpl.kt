package ua.anime.animecraft.analytics.impl

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import ua.anime.animecraft.analytics.api.AnalyticsApi
import com.animecraft.core.log.warn

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

class AnalyticsApiImpl @Inject constructor(
    private val firebaseAnalyticsApi: FirebaseAnalyticsApi
) : AnalyticsApi {

    private companion object {
        const val TAG = "AnalyticsApiImpl"
    }

    override fun logEvent(event: String, params: Map<String, Any?>?) {
        val arguments = if (params == null) null else paramsToBundle(params)
        firebaseAnalyticsApi.logEvent(event, arguments)
    }

    private fun paramsToBundle(arguments: Map<String, Any?>): Bundle {
        return Bundle(arguments.size).apply {
            for ((key, value) in arguments) {
                when (value) {
                    is Double -> putDouble(key, value)
                    is Long -> putLong(key, value)
                    is Int -> putInt(key, value)
                    is String -> putString(key, value)

                    else -> {
                        val valueType = value?.javaClass?.canonicalName ?: "null"
                        com.animecraft.core.log.warn(TAG) { "Illegal value type $valueType for key \"$key\"" }
                    }
                }
            }
        }
    }

    override fun setCurrentScreen(screenName: String) {
        val event = FirebaseAnalytics.Event.SCREEN_VIEW
        val params = bundleOf(
            Pair(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        )
        firebaseAnalyticsApi.logEvent(event, params)

        // log simple event, e.g. screen_home
        val split = screenName.split(Regex("(?=\\p{Upper})"))
        val screenEvent = "screen${split.joinToString("_", "", "", -1, "", ::transformToEvent)}"
        logEvent(screenEvent, null)
    }

    private fun transformToEvent(screenEvent: String): CharSequence {
        return screenEvent.lowercase()
    }
}
