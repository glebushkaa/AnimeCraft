package ua.anime.animecraft.ad.api

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

interface AppOpenAdApi {
    /**
     * Loads the app open ad for specified [adUnitId].
     *
     * @param adUnitId The string of ad id to be loaded.
     *
     * @throws [AdException] If any errors occur during loading the ad.
     */
    suspend fun loadAppOpenAd(adUnitId: String)

    /**
     * Shows the app open ad for specified [adUnitId].
     *
     * @param adUnitId The string of ad id to be shown.
     *
     * @throws [AdException] If any errors occur during showing the ad.
     */
    suspend fun showAppOpenAd(adUnitId: String)
}