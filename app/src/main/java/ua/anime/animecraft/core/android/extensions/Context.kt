package ua.anime.animecraft.core.android.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.text.TextUtils
import androidx.core.content.ContextCompat
import ua.anime.animecraft.R
import ua.anime.animecraft.core.log.error
import ua.anime.animecraft.core.log.tag
import java.util.Locale

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

private const val SHARE_TYPE = "text/plain"
private const val SHARE_TITLE = "Share app"

fun Context.updateLanguage(language: String, country: String) {
    val locale: Locale = if (TextUtils.isEmpty(country)) {
        Locale(language)
    } else {
        Locale(language, country)
    }
    Locale.setDefault(locale)
    val configuration = resources.configuration.apply {
        setLocale(locale)
    }
    resources.updateConfiguration(configuration, resources.displayMetrics)
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
