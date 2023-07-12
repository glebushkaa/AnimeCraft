package com.animecraft.feature.info

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.lifecycle.SavedStateHandle
import coil.compose.AsyncImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
import com.animecraft.core.domain.usecase.preferences.GetDownloadDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.skin.GetCategoryNameByIdUseCase
import com.animecraft.core.domain.usecase.skin.GetSkinFlowUseCase
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 *
 * TODO MOVE ALL LAUNCHES TO IO OR DEFAULT SCOPE
 *
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val getSkinFlowUseCase: GetSkinFlowUseCase,
    private val saveSkinGameUseCase: SaveSkinGameUseCase,
    private val updateSkinFavoriteStateUseCase: UpdateSkinFavoriteStateUseCase,
    private val getCategoryNameByIdUseCase: GetCategoryNameByIdUseCase,
    savedStateHandle: SavedStateHandle
) : AnimeCraftViewModel() {

    init {
        collectSkin()
        collectDownloadDialogDisabled()
    }

    private val _screenState = MutableStateFlow(InfoScreenState())
    val screenState = _screenState.asStateFlow()

    private val id: Int? = savedStateHandle["skinId"]
    private var downloadDialogDisabled = false

    fun updateSkinFavoriteState() = viewModelScope.launch {
        val skin = _screenState.value.skin ?: return@launch
        val params = UpdateSkinFavoriteStateUseCase.Params(skin.id, skin.favorite.not())
        updateSkinFavoriteStateUseCase(params)
    }

    fun checkImageLoadingState(
        imageLoadingState: AsyncImagePainter.State
    ) = viewModelScope.launch {
        val loading = imageLoadingState !is AsyncImagePainter.State.Success
        val state = _screenState.value.copy(loading = loading)
        _screenState.emit(state)
    }

    fun saveGameSkinImage() = viewModelScope.launch {
        val gameImageFileName = _screenState.value.skin?.gameImageFileName ?: return@launch
        val ableToSaveInGame = SDK_INT <= Build.VERSION_CODES.Q
        val params = SaveSkinGameUseCase.Params(gameImageFileName, ableToSaveInGame)
        val isResultSuccessful = saveSkinGameUseCase(params).isSuccess
        val state = _screenState.value.copy(downloadState = Event(isResultSuccessful))
        _screenState.emit(state)
    }

    fun showDownloadDialog() = viewModelScope.launch {
        val state = _screenState.value.copy(downloadDialogShown = Event(true))
        _screenState.emit(state)
    }

    private fun collectSkin() = viewModelScope.launch {
        val id = id ?: return@launch
        val params = GetSkinFlowUseCase.Params(id)
        getSkinFlowUseCase(params).getOrNull()?.collect {
            val state = _screenState.value.copy(skin = it)
            _screenState.emit(state)
            updateCategoryState(it.categoryId)
        }
    }

    private fun updateCategoryState(id: Int?) = viewModelScope.launch {
        val state = if (id != null) {
            val params = GetCategoryNameByIdUseCase.Params(id)
            val name = getCategoryNameByIdUseCase(params).getOrNull()
            _screenState.value.copy(categoryName = name, categoryVisible = true)
        } else {
            _screenState.value.copy(categoryName = null, categoryVisible = false)
        }
        _screenState.emit(state)
    }

    private fun collectDownloadDialogDisabled() = viewModelScope.launch {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect {
            downloadDialogDisabled = it
        }
    }
}
