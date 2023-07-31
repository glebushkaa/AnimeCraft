package com.animecraft.analytics.api

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

interface AnalyticsApi {

    /**
     * Sets the current [screenName].
     *
     * @param screenName The name of the current screen.
     */
    fun setCurrentScreen(screenName: String)

    /**
     * Sets the user rating
     *
     * @param rating The rating of the user
     */
    fun sendRating(rating: Int)
}
