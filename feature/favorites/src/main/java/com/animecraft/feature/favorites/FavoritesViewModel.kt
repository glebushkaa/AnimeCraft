package com.animecraft.feature.favorites

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.animecraft.animecraft.common.filterListByName
import com.animecraft.animecraft.common.replaceAllElements
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
import com.animecraft.core.domain.usecase.preferences.download.GetDownloadDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.skin.GetFavoritesSkinsFlowUseCase
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase
import com.animecraft.model.Skin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val updateSkinFavoriteStateUseCase: UpdateSkinFavoriteStateUseCase,
    private val getFavoritesSkinsFlowUseCase: GetFavoritesSkinsFlowUseCase,
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val saveSkinGameUseCase: SaveSkinGameUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(FavoriteScreenState())
    val screenState = _screenState.asStateFlow()

    private val favorites = mutableListOf<Skin>()

    private var downloadDialogDisabled = false

    init {
        collectFavoritesFlow()
        collectDialogDisabledFlow()
    }

    fun updateSearchInput(input: String) = viewModelScope.launch(dispatchersProvider.io()) {
        val state = _screenState.value.copy(searchInput = input)
        _screenState.emit(state)
        emitFilteredSkins()
    }

    fun showDownloadDialog() = viewModelScope.launch(dispatchersProvider.io()) {
        val state = _screenState.value.copy(
            downloadDialogShown = Event(true)
        )
        _screenState.emit(state)
    }

    fun updateFavoriteSkin(id: Int) = viewModelScope.launch(dispatchersProvider.io()) {
        val favorite = favorites.find { it.id == id }?.favorite?.not() ?: false
        val params = UpdateSkinFavoriteStateUseCase.Params(
            skinId = id,
            favorite = favorite
        )
        updateSkinFavoriteStateUseCase(params)
    }

    fun saveGameSkinImage(id: Int) = viewModelScope.launch(dispatchersProvider.io()) {
        val gameImageFileName = favorites.find { it.id == id }?.gameImageFileName ?: return@launch
        val ableToSaveInGame = SDK_INT >= Build.VERSION_CODES.Q
        val params = SaveSkinGameUseCase.Params(gameImageFileName, ableToSaveInGame)
        val result = saveSkinGameUseCase(params)
        val state = _screenState.value.copy(
            downloadState = Event(result.isSuccess)
        )
        _screenState.emit(state)
    }

    private fun emitFilteredSkins() = viewModelScope.launch(dispatchersProvider.io()) {
        val searchInput = _screenState.value.searchInput
        val list = favorites.filterListByName(searchInput)
        val state = _screenState.value.copy(favorites = list)
        _screenState.emit(state)
    }

    private fun collectFavoritesFlow() = viewModelScope.launch(dispatchersProvider.io()) {
        getFavoritesSkinsFlowUseCase().getOrNull()?.collect { items ->
            favorites.replaceAllElements(items)
            emitFilteredSkins()
        }
    }

    private fun collectDialogDisabledFlow() = viewModelScope.launch(dispatchersProvider.io()) {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect { disabled ->
            downloadDialogDisabled = disabled
        }
    }
}
