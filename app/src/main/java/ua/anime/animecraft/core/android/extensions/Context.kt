package ua.anime.animecraft.core.android.extensions

import android.content.Context
import android.text.TextUtils
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
