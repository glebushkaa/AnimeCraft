package ua.anime.animecraft.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import ua.anime.animecraft.R
import ua.anime.animecraft.worker.SkinsWorkManager.Companion.WORK_SKINS_CHANNEL_ID

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun Context.createDownloadNotification() =
    NotificationCompat.Builder(this, WORK_SKINS_CHANNEL_ID)
        .setContentText(getString(R.string.worker_notification_text))
        .setContentTitle(getString(R.string.worker_notification_title))
        .setSmallIcon(R.drawable.test_picture)
        .setSilent(true)
        .build()
