package ua.animecraft.analytics.api

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

interface AnalyticsApi {

    /**
     * Logs an app event.
     *
     * @param event The name of the event. Should contain 1 to 40 alphanumeric characters or
     * underscores. The name must start with an alphabetic character.
     * @param params The map of event parameters. Passing null indicates that the event has no
     * parameters. [String], [Long] and [Double] param types are supported.
     */
    fun logEvent(event: String, params: Map<String, Any?>?)

    /**
     * Sets the current [screenName].
     *
     * @param screenName The name of the current screen.
     */
    fun setCurrentScreen(screenName: String)
}
