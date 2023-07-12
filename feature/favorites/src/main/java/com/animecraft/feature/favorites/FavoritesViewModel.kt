package com.animecraft.feature.favorites

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.anime.animecraft.core.android.ResourceEvent
import com.anime.animecraft.core.android.extensions.toResourceEvent
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
import com.animecraft.core.domain.usecase.preferences.GetDownloadDialogDisabledFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.animecraft.common.filterListByName
import com.animecraft.animecraft.common.replaceAllElements
import com.animecraft.model.Skin
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val updateSkinFavoriteStateUseCase: com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase,
    private val getFavoritesSkinsFlowUseCase: com.animecraft.core.domain.usecase.skin.GetFavoritesSkinsFlowUseCase,
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val saveSkinGameUseCase: com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(FavoriteScreenState())
    val screenState = _screenState.asStateFlow()

    private val _favoritesFlow = MutableStateFlow(listOf<Skin>())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    private val _downloadFlow = MutableStateFlow<ResourceEvent<Unit>?>(null)
    val downloadFlow = _downloadFlow.asStateFlow()

    private val favorites = mutableListOf<Skin>()

    private var downloadDialogDisabled = false

    init {
        collectFavoritesFlow()
        collectDialogDisabledFlow()
    }

    fun updateSearchInput(input: String) = viewModelScope.launch {
        val state = _screenState.value.copy(searchInput = input)
        _screenState.emit(state)
        emitFilteredSkins()
    }

    fun showDownloadDialog() = viewModelScope.launch {
        val state = _screenState.value.copy(
            downloadDialogShown = Event(true)
        )
        _screenState.emit(state)
    }

    fun updateFavoriteSkin(id: Int) = viewModelScope.launch {
        val favorite = favorites.find { it.id == id }?.favorite?.not() ?: false
        val params = com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase.Params(
            skinId = id, favorite = favorite
        )
        updateSkinFavoriteStateUseCase(params)
    }

    fun saveGameSkinImage(id: Int) = viewModelScope.launch {
        val gameImageFileName = favorites.find { it.id == id }?.gameImageFileName ?: return@launch
        val ableToSaveInGame = SDK_INT >= Build.VERSION_CODES.Q
        val params = SaveSkinGameUseCase.Params(gameImageFileName, ableToSaveInGame)
        val result = saveSkinGameUseCase(params).toResourceEvent()
        _downloadFlow.emit(result)
    }

    private fun emitFilteredSkins() = viewModelScope.launch {
        val searchInput = _screenState.value.searchInput
        val list = favorites.filterListByName(searchInput)
        val state = _screenState.value.copy(favorites = list)
        _screenState.emit(state)
    }

    private fun collectFavoritesFlow() = viewModelScope.launch {
        getFavoritesSkinsFlowUseCase().getOrNull()?.collect { items ->
            favorites.replaceAllElements(items)
            emitFilteredSkins()
        }
    }

    private fun collectDialogDisabledFlow() = viewModelScope.launch {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect { disabled ->
            downloadDialogDisabled = disabled
        }
    }
}
