package com.animecraft.feature.language

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.domain.usecase.preferences.GetLanguageFlowUseCase
import com.animecraft.core.domain.usecase.preferences.UpdateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.model.Language
import java.util.Locale
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getLanguageFlowUseCase: GetLanguageFlowUseCase,
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(LanguageScreenState())
    val screenState = _screenState.asStateFlow()

    private val userLocale = Locale.getDefault().language

    init {
        collectLanguageFlow()
    }

    fun updateDropdownState() = viewModelScope.launch {
        val dropdownState = _screenState.value.dropdownExpanded
        val state = _screenState.value.copy(dropdownExpanded = !dropdownState)
        _screenState.emit(state)
    }

    fun updateSelectedLanguage(language: Language) = viewModelScope.launch {
        val selectedLanguage = _screenState.value.selectedLanguage
        if (selectedLanguage.id == language.id) return@launch
        val state = _screenState.value.copy(
            selectedLanguage = language,
            dropdownExpanded = false
        )
        _screenState.emit(state)
    }

    fun changeLanguage() = viewModelScope.launch {
        val language = _screenState.value.selectedLanguage
        val params = UpdateLanguageUseCase.Params(language.locale)
        updateLanguageUseCase(params)
    }

    private fun collectLanguageFlow() = viewModelScope.launch {
        getLanguageFlowUseCase().getOrNull()?.collect {
            val language = languagesList.find { lang -> lang.locale == it } ?: run {
                languagesList.find { item -> item.locale == userLocale } ?: languagesList[0]
            }
            val state = _screenState.value.copy(selectedLanguage = language)
            _screenState.emit(state)
        }
    }
}