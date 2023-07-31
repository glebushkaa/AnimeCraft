package com.animecraft.feature.language

import com.animecraft.model.Language
import ua.anime.animecraft.feature.language.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

const val ENGLISH = "en"
const val RUSSIAN = "ru"
const val UKRAINIAN = "uk"
const val FRENCH = "fr"
const val GERMAN = "de"
const val SPANISH = "es"
const val JAPANESE = "ja"
const val POLISH = "pl"

val languagesList = listOf(
    Language(
        id = 0,
        nameResId = R.string.english,
        iconResId = R.drawable.ic_usa_flag,
        locale = ENGLISH
    ),
    Language(
        id = 1,
        nameResId = R.string.ukrainian,
        iconResId = R.drawable.ic_ukr_flag,
        locale = UKRAINIAN
    ),
    Language(
        id = 2,
        nameResId = R.string.russian,
        iconResId = R.drawable.ic_ru_flag,
        locale = RUSSIAN
    ),
    Language(
        id = 3,
        nameResId = R.string.spanish,
        iconResId = R.drawable.ic_esp_flag,
        locale = SPANISH
    ),
    Language(
        id = 4,
        nameResId = R.string.german,
        iconResId = R.drawable.ic_ger_flag,
        locale = GERMAN
    ),
    Language(
        id = 5,
        nameResId = R.string.french,
        iconResId = R.drawable.ic_fr_flag,
        locale = FRENCH
    ),
    Language(
        id = 6,
        nameResId = R.string.polish,
        iconResId = R.drawable.ic_pl_flag,
        locale = POLISH
    ),
    Language(
        id = 7,
        nameResId = R.string.japanese,
        iconResId = R.drawable.ic_jp_flag,
        locale = JAPANESE
    )
)
