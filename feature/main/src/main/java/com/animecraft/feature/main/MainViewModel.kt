package com.animecraft.feature.main

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.animecraft.core.domain.usecase.category.GetCategoriesFlowUseCase
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
import com.animecraft.core.domain.usecase.preferences.GetDownloadDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.preferences.GetRateCompletedFlowUseCase
import com.animecraft.core.domain.usecase.preferences.GetRateDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.skin.GetAllSkinsFlowUseCase
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.animecraft.common.TWO_SECONDS
import com.animecraft.animecraft.common.filterListByCategoryId
import com.animecraft.animecraft.common.filterListByName
import com.animecraft.animecraft.common.performIf
import com.animecraft.animecraft.common.replaceAllElements
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.log.debug
import com.animecraft.model.Category
import com.animecraft.model.Skin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getAllSkinsFlowUseCase: GetAllSkinsFlowUseCase,
    private val updateSkinFavoriteStateUseCase: UpdateSkinFavoriteStateUseCase,
    private val getCategoriesFlowUseCase: GetCategoriesFlowUseCase,
    private val saveSkinGameUseCase: SaveSkinGameUseCase,
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val getRateDialogDisabledFlowUseCase: GetRateDialogDisabledFlowUseCase,
    private val getRateCompletedFlowUseCase: GetRateCompletedFlowUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState = _screenState.asStateFlow()

    private val skins = mutableListOf<Skin>()

    private var downloadDialogDisabled = false
    private var rateDialogDisabled = false
    private var rateCompleted = false
    private var timesAppOpened = 0

    init {
        collectCategories()
        collectSkins()
        collectDownloadDialogDisabled()
        collectRateDialogDisabled()
        collectRateCompleted()
        tryShowRateDialog()
    }

    fun updateSearchInput(input: String) = viewModelScope.launch(dispatchersProvider.io()) {
        val state = _screenState.value.copy(searchInput = input)
        _screenState.emit(state)
        emitFilteredSkins()
    }

    fun updateSelectedCategory(category: Category?) =
        viewModelScope.launch(dispatchersProvider.io()) {
            val selectedCategoryId = _screenState.value.selectedCategory?.id
            val _category = if (category?.id == selectedCategoryId) null else category
            val state = _screenState.value.copy(selectedCategory = _category)
            _screenState.emit(state)
            emitFilteredSkins()
        }

    fun tryShowDownloadDialog() = viewModelScope.launch(dispatchersProvider.io()) {
        if (downloadDialogDisabled) return@launch
        val state = _screenState.value.copy(downloadDialogShown = Event(true))
        _screenState.emit(state)
    }

    fun saveGameSkinImage(id: Int) = viewModelScope.launch(dispatchersProvider.io()) {
        val gameImageFileName = skins.find { it.id == id }?.gameImageFileName ?: return@launch
        val ableToSaveInGame = SDK_INT <= VERSION_CODES.Q
        val params = SaveSkinGameUseCase.Params(gameImageFileName, ableToSaveInGame)
        val isResultSuccessful = saveSkinGameUseCase(params).isSuccess
        val state = _screenState.value.copy(downloadState = Event(isResultSuccessful))
        _screenState.emit(state)
    }

    fun updateFavoriteSkin(id: Int) = viewModelScope.launch(dispatchersProvider.io()) {
        val favorite = skins.find { it.id == id }?.favorite?.not() ?: false
        val params = UpdateSkinFavoriteStateUseCase.Params(id, favorite)
        updateSkinFavoriteStateUseCase(params)
    }

    private fun emitFilteredSkins() = viewModelScope.launch(dispatchersProvider.io()) {
        val category = _screenState.value.selectedCategory
        val searchInput = _screenState.value.searchInput
        val list = skins.performIf(searchInput.isNotEmpty()) {
            filterListByName(searchInput)
        }.performIf(category != null) {
            filterListByCategoryId(category!!.id)
        }.toList()
        val state = _screenState.value.copy(skins = list)
        _screenState.emit(state)
    }
    private fun collectCategories() = viewModelScope.launch(dispatchersProvider.io()) {
        getCategoriesFlowUseCase().getOrNull()?.collect {
            val state = _screenState.value.copy(categories = it)
            _screenState.emit(state)
        }
    }

    private fun tryShowRateDialog() = viewModelScope.launch(dispatchersProvider.io()) {
        delay(TWO_SECONDS)
        if (!shouldRateDialogBeShown()) return@launch
        val state = _screenState.value.copy(rateDialogShown = Event(true))
        _screenState.emit(state)
    }

    private fun collectSkins() = viewModelScope.launch(dispatchersProvider.io()) {
        getAllSkinsFlowUseCase().getOrNull()?.collect {
            skins.replaceAllElements(it)
            val state = _screenState.value.copy(isLoading = true )
            _screenState.emit(state)
            emitFilteredSkins()
        }
    }

    private fun collectDownloadDialogDisabled() = viewModelScope.launch(dispatchersProvider.io()) {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect {
            downloadDialogDisabled = it
        }
    }

    private fun collectRateDialogDisabled() = viewModelScope.launch(dispatchersProvider.io()) {
        getRateDialogDisabledFlowUseCase().getOrNull()?.collect {
            rateDialogDisabled = it
        }
    }

    private fun collectRateCompleted() = viewModelScope.launch(dispatchersProvider.io()) {
        getRateCompletedFlowUseCase().getOrNull()?.collect {
            rateCompleted = it
        }
    }

    private fun shouldRateDialogBeShown(): Boolean {
        return !rateDialogDisabled &&
            timesAppOpened % EVERY_THIRD_OPEN == 0 &&
            timesAppOpened >= EVERY_THIRD_OPEN &&
            !rateCompleted
    }

    private companion object {
        private const val EVERY_THIRD_OPEN = 3
    }
}