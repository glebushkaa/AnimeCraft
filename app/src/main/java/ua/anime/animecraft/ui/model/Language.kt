package ua.anime.animecraft.ui.model

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

data class Language(
    val id: Int,
    val iconResId: Int,
    val nameResId: Int,
    val languageLocale: String,
    val countryLocale: String
)
