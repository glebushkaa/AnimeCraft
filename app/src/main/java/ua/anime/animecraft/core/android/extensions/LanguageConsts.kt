package ua.anime.animecraft.core.android.extensions

import ua.anime.animecraft.R
import ua.anime.animecraft.ui.model.Language

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

const val ENGLISH = "en"
const val RUSSIAN = "ru"
const val UKRAINIAN = "uk"
const val FRENCH = "fr"
const val GERMAN = "de"
const val SPANISH = "es"
const val JAPANESE = "ja"
const val POLISH = "pl"

const val US_COUNTRY = "en"
const val RU_COUNTRY = "ru"
const val UK_COUNTRY = "uk"
const val FR_COUNTRY = "fr"
const val DE_COUNTRY = "de"
const val SP_COUNTRY = "es"
const val JAP_COUNTRY = "ja"
const val PL_COUNTRY = "pl"

val languagesList = listOf(
    Language(
        id = 0,
        nameResId = R.string.english,
        iconResId = R.drawable.ic_usa_flag,
        languageLocale = ENGLISH,
        countryLocale = US_COUNTRY
    ),
    Language(
        id = 1,
        nameResId = R.string.ukrainian,
        iconResId = R.drawable.ic_ukr_flag,
        languageLocale = UKRAINIAN,
        countryLocale = UK_COUNTRY
    ),
    Language(
        id = 2,
        nameResId = R.string.russian,
        iconResId = R.drawable.ic_ru_flag,
        languageLocale = RUSSIAN,
        countryLocale = RU_COUNTRY
    ),
    Language(
        id = 3,
        nameResId = R.string.spanish,
        iconResId = R.drawable.ic_esp_flag,
        languageLocale = SPANISH,
        countryLocale = SP_COUNTRY
    ),
    Language(
        id = 4,
        nameResId = R.string.german,
        iconResId = R.drawable.ic_ger_flag,
        languageLocale = GERMAN,
        countryLocale = DE_COUNTRY
    ),
    Language(
        id = 5,
        nameResId = R.string.french,
        iconResId = R.drawable.ic_fr_flag,
        languageLocale = FRENCH,
        countryLocale = FR_COUNTRY
    ),
    Language(
        id = 6,
        nameResId = R.string.polish,
        iconResId = R.drawable.ic_pl_flag,
        languageLocale = POLISH,
        countryLocale = PL_COUNTRY
    ),
    Language(
        id = 7,
        nameResId = R.string.japanese,
        iconResId = R.drawable.ic_jp_flag,
        languageLocale = JAPANESE,
        countryLocale = JAP_COUNTRY
    )
)
