package com.animecraft.analytics.impl

import androidx.core.os.bundleOf
import com.animecraft.analytics.api.AnalyticsApi
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

class AnalyticsApiImpl @Inject constructor(
    private val firebaseAnalyticsApi: FirebaseAnalyticsApi
) : AnalyticsApi {

    override fun sendRating(rating: Int) {
        val event = FirebaseAnalytics.Event.POST_SCORE
        val params = bundleOf(
            Pair(FirebaseAnalytics.Param.SCORE, rating)
        )
        firebaseAnalyticsApi.logEvent(event, params)
    }

    override fun setCurrentScreen(screenName: String) {
        val event = FirebaseAnalytics.Event.SCREEN_VIEW
        val params = bundleOf(
            Pair(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        )
        firebaseAnalyticsApi.logEvent(event, params)
    }
}
