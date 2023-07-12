package com.anime.animecraft.download.manager

import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.anime.animecraft.core.download.manager.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

class SkinsDownloadManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    var gameDownloadIdsMap = mutableMapOf<Int, Long>()

    /**
     * We have to check is file exists and is readable before download will be started,
     * whether file exists and is readable we don't to download it
     */

    fun getDownloadGameImagesByUrlRequest(
        gameUrl: String,
        gameFileName: String
    ): DownloadManager.Request? {
        val mediaDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(mediaDirectory, gameFileName)
        if (file.exists() && file.canRead()) return null
        file.delete()
        return DownloadManager.Request(Uri.parse(gameUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationUri(Uri.fromFile(file))
            .setTitle(gameFileName)
            .setDescription(context.getString(R.string.download))
            .setAllowedNetworkTypes(NETWORK_MOBILE or NETWORK_WIFI)
    }
}
