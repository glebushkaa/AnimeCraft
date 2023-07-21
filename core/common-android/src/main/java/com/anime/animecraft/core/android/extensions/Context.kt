package com.anime.animecraft.core.android.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.anime.animecraft.core.common.android.R
import com.animecraft.core.log.error
import com.animecraft.core.log.tag

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

private const val SHARE_TYPE = "text/plain"
private const val SHARE_TITLE = "Share app"

fun Context.permissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) ==
        PackageManager.PERMISSION_GRANTED
}

fun Context.updateLanguage(
    language: String
) {
    (this as? Activity)?.runOnUiThread {
        val appLocale = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}

fun Context.shareApp(link: String) {
    try {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = SHARE_TYPE
            putExtra(EXTRA_TEXT, link)
        }
        startActivity(Intent.createChooser(intent, SHARE_TITLE))
    } catch (exception: ActivityNotFoundException) {
        error(this@shareApp.tag(), exception) { exception.message ?: "" }
    }
}

fun Context.sendFeedback(message: String) {
    val context = this
    val intent = Intent.createChooser(
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse(getString(R.string.mailto))
            putExtra(EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
            putExtra(EXTRA_TEXT, message.trim())
            putExtra(EXTRA_SUBJECT, getString(R.string.animecraft_feedback))
        },
        getString(R.string.animecraft_feedback)
    )
    ContextCompat.startActivity(context, intent, null)
}

fun Context.sendReport(message: String) {
    val context = this
    val intent = Intent.createChooser(
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse(getString(R.string.mailto))
            putExtra(EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
            putExtra(EXTRA_TEXT, message.trim())
            putExtra(EXTRA_SUBJECT, getString(R.string.animecraft_report))
        },
        getString(R.string.animecraft_report)
    )
    ContextCompat.startActivity(context, intent, null)
}

fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}
