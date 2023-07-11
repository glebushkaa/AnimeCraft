package ua.animecraft.analytics.impl

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

class FirebaseAnalyticsApi @Inject constructor(
    @ApplicationContext context: Context
) {

    private val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(event: String, params: Bundle?) {
        firebaseAnalytics.logEvent(event, params)
    }
}
