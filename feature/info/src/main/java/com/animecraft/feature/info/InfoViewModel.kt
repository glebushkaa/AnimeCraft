@file:Suppress("LongParameterList")

package com.animecraft.feature.info

import androidx.lifecycle.SavedStateHandle
import coil.compose.AsyncImagePainter
import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
import com.animecraft.core.domain.usecase.preferences.download.GetDownloadDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.skin.GetCategoryNameByIdUseCase
import com.animecraft.core.domain.usecase.skin.GetSkinFlowUseCase
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val getSkinFlowUseCase: GetSkinFlowUseCase,
    private val saveSkinGameUseCase: SaveSkinGameUseCase,
    private val updateSkinFavoriteStateUseCase: UpdateSkinFavoriteStateUseCase,
    private val getCategoryNameByIdUseCase: GetCategoryNameByIdUseCase,
    savedStateHandle: SavedStateHandle
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(InfoScreenState())
    val screenState = _screenState.asStateFlow()

    private val id: Int? = savedStateHandle["skinId"]
    private var downloadDialogDisabled = false

    init {
        collectSkin()
        collectDownloadDialogDisabled()
    }

    fun updateSkinFavoriteState() = viewModelScope.launch(dispatchersProvider.io()) {
        val skin = _screenState.value.skin ?: return@launch
        val params = UpdateSkinFavoriteStateUseCase.Params(skin.id, skin.favorite.not())
        updateSkinFavoriteStateUseCase(params)
    }

    fun checkImageLoadingState(
        imageLoadingState: AsyncImagePainter.State
    ) = viewModelScope.launch(dispatchersProvider.io()) {
        val loading = imageLoadingState !is AsyncImagePainter.State.Success
        val state = _screenState.value.copy(loading = loading)
        _screenState.emit(state)
    }

    fun saveGameSkinImage() = viewModelScope.launch(dispatchersProvider.io()) {
        val gameImageFileName = _screenState.value.skin?.gameImageFileName ?: return@launch
        val id = _screenState.value.skin?.id ?: return@launch
        val params = SaveSkinGameUseCase.Params(id, gameImageFileName)
        val isResultSuccessful = saveSkinGameUseCase(params).isSuccess
        val state = _screenState.value.copy(downloadState = Event(isResultSuccessful))
        _screenState.emit(state)
    }

    fun showDownloadDialog() = viewModelScope.launch(dispatchersProvider.io()) {
        if (downloadDialogDisabled) return@launch
        val state = _screenState.value.copy(downloadDialogShown = Event(true))
        _screenState.emit(state)
    }

    private fun collectSkin() = viewModelScope.launch(dispatchersProvider.io()) {
        val id = id ?: return@launch
        val params = GetSkinFlowUseCase.Params(id)
        getSkinFlowUseCase(params).getOrNull()?.collect {
            val state = _screenState.value.copy(skin = it)
            _screenState.emit(state)
            updateCategoryState(it.categoryId)
        }
    }

    private fun updateCategoryState(id: Int?) = viewModelScope.launch(dispatchersProvider.io()) {
        val categoryName = id?.let {
            val params = GetCategoryNameByIdUseCase.Params(id)
            getCategoryNameByIdUseCase(params).getOrNull()
        }
        val state = _screenState.value.copy(
            categoryName = categoryName,
            categoryVisible = categoryName != null
        )
        _screenState.emit(state)
    }

    private fun collectDownloadDialogDisabled() = viewModelScope.launch(dispatchersProvider.io()) {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect {
            downloadDialogDisabled = it
        }
    }
}
