package com.animecraft.feature.language

import com.animecraft.model.Language

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

data class LanguageScreenState(
    val selectedLanguage: Language = languagesList[0],
    val dropdownExpanded: Boolean = false,
    val changeEnabled: Boolean = false,
    val languages: List<Language> = languagesList
)
