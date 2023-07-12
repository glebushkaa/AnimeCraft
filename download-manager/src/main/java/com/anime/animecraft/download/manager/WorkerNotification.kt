package com.anime.animecraft.download.manager

import android.content.Context
import androidx.core.app.NotificationCompat
import com.anime.animecraft.core.download.manager.R
import com.anime.animecraft.download.manager.SkinsWorkManager.Companion.WORK_SKINS_CHANNEL_ID

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun Context.createDownloadNotification() =
    NotificationCompat.Builder(this, WORK_SKINS_CHANNEL_ID)
        .setContentText(getString(R.string.worker_notification_text))
        .setContentTitle(getString(R.string.worker_notification_title))
        .setSmallIcon(R.drawable.download_notification_icon)
        .setSilent(true)
        .build()
