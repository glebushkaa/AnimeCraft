package ua.anime.animecraft.core.android.extensions

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.text.TextUtils
import androidx.core.content.ContextCompat
import java.util.Locale

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

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

/**
 * TODO change email
 * TODO extract strings to resources
 */
fun Context.sendFeedback(message: String) {
    val context = this
    val intent = Intent.createChooser(
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
            putExtra(EXTRA_EMAIL, arrayOf("gleb.mokryy@gmail.com"))
            putExtra(EXTRA_TEXT, message.trim())
            putExtra(EXTRA_SUBJECT, "AnimeCraft Feedback")
        },
        "AnimeCraft Feedback"
    )
    ContextCompat.startActivity(context, intent, null)
}
