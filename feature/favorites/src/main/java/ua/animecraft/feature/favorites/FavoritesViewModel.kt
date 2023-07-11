package ua.animecraft.feature.favorites

import com.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.android.ResourceEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.core.android.extensions.toResourceEvent
import ua.animecraft.core.extensions.replaceAllElements
import ua.animecraft.core.components.extensions.filterListByName
import ua.anime.animecraft.ui.model.Skin
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val updateSkinFavoriteStateUseCase: com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase,
    private val getFavoritesSkinsFlowUseCase: com.animecraft.core.domain.usecase.skin.GetFavoritesSkinsFlowUseCase,
    private val checkDownloadDialogDisabledUseCase: com.animecraft.core.domain.usecase.prefs.CheckDownloadDialogDisabledUseCase,
    private val changeDisableDownloadDialogPrefUseCase: com.animecraft.core.domain.usecase.prefs.ChangeDisableDownloadDialogPrefUseCase,
    private val saveSkinGameUseCase: com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase
) : AnimeCraftViewModel() {

    private val _favoritesFlow = MutableStateFlow(listOf<Skin>())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    private val _downloadFlow = MutableStateFlow<ResourceEvent<Unit>?>(null)
    val downloadFlow = _downloadFlow.asStateFlow()

    private val favorites = mutableListOf<Skin>()

    private var currentSearchInput: String = ""

    var isDownloadDialogDisabled = false
        private set

    init {
        collectFavoritesFlow()
        checkIsDownloadDialogDisabled()
    }

    fun searchSkins(name: String) = viewModelScope.launch {
        currentSearchInput = name
        val list = favorites.filterListByName(name)
        _favoritesFlow.emit(list)
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
        _downloadFlow.emit(ResourceEvent.Loading)
        val ableToSaveInGame = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        val params = com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase.Params(gameImageFileName,ableToSaveInGame)
        val result = saveSkinGameUseCase(params).toResourceEvent()
        _downloadFlow.emit(result)
    }

    fun disableDownloadDialogOpen() = viewModelScope.launch {
        val params = com.animecraft.core.domain.usecase.prefs.ChangeDisableDownloadDialogPrefUseCase.Params(true)
        changeDisableDownloadDialogPrefUseCase(params)
    }

    private fun checkIsDownloadDialogDisabled() = viewModelScope.launch {
        isDownloadDialogDisabled = checkDownloadDialogDisabledUseCase().getOrDefault(false)
    }

    private fun collectFavoritesFlow() = viewModelScope.launch {
        getFavoritesSkinsFlowUseCase().onSuccess {
            it.collect { items ->
                favorites.replaceAllElements(items)
                val filteredList = favorites.filterListByName(currentSearchInput)
                _favoritesFlow.emit(filteredList)
            }
        }
    }
}
