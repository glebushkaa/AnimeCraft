package com.animecraft.feature.language

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.preferences.language.GetLanguageFlowUseCase
import com.animecraft.core.domain.usecase.preferences.language.UpdateLanguageUseCase
import com.animecraft.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getLanguageFlowUseCase: GetLanguageFlowUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(LanguageScreenState())
    val screenState = _screenState.asStateFlow()

    private val userLocale = Locale.getDefault().language

    private var currentLanguage: Language = languagesList[0]

    init {
        collectLanguageFlow()
    }

    fun updateDropdownState() = viewModelScope.launch(dispatchersProvider.io()) {
        val dropdownState = _screenState.value.dropdownExpanded
        val state = _screenState.value.copy(dropdownExpanded = !dropdownState)
        _screenState.emit(state)
    }

    fun updateSelectedLanguage(
        language: Language
    ) = viewModelScope.launch(dispatchersProvider.io()) {
        val state = _screenState.value.copy(
            selectedLanguage = language,
            dropdownExpanded = false,
            changeEnabled = currentLanguage.id != language.id
        )
        _screenState.emit(state)
    }

    fun changeLanguage() = viewModelScope.launch(dispatchersProvider.io()) {
        val language = _screenState.value.selectedLanguage
        val params = UpdateLanguageUseCase.Params(language.locale)
        updateLanguageUseCase(params)
    }

    private fun collectLanguageFlow() = viewModelScope.launch(dispatchersProvider.io()) {
        getLanguageFlowUseCase().getOrNull()?.collect {
            currentLanguage = languagesList.find { lang -> lang.locale == it } ?: run {
                languagesList.find { item -> item.locale == userLocale } ?: languagesList[0]
            }
            updateSelectedLanguage(currentLanguage)
        }
    }
}
