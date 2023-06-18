package ua.anime.animecraft.ui.screens.favorites

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.core.android.Event
import ua.anime.animecraft.core.common.replaceAllElements
import ua.anime.animecraft.data.files.SkinFilesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.ui.extensions.filterListByName
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val skinsPreferencesHandler: SkinsPreferencesHandler,
    private val skinFilesHandler: SkinFilesHandler
) : AnimeCraftViewModel() {

    private val _favoritesFlow = MutableStateFlow(listOf<Skin>())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    private val _downloadFlow = MutableStateFlow<Event<Boolean?>>(Event(null))
    val downloadFlow = _downloadFlow.asStateFlow()

    private val favorites = mutableListOf<Skin>()

    private var currentSearchInput: String = ""

    val isDownloadDialogDisabled = skinsPreferencesHandler.getBoolean(
        IS_DOWNLOAD_DIALOG_DISABLED
    ) ?: false

    init {
        getFavorites()
    }

    fun saveGameSkinImage(id: Int) = viewModelScope.launch(Dispatchers.Default) {
        val gameImageFileName = favorites.find { it.id == id }?.gameImageFileName ?: return@launch
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            skinFilesHandler.saveSkinToGallery(gameImageFileName)
        } else {
            skinFilesHandler.saveSkinToMinecraft(gameImageFileName)
        }
        _downloadFlow.emit(Event(result.isSuccess))
    }

    fun disableDownloadDialogOpen() {
        skinsPreferencesHandler.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, true)
    }

    fun getFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavoritesSkins().collect {
                favorites.replaceAllElements(it)
                val filteredList = favorites.filterListByName(currentSearchInput)
                _favoritesFlow.emit(filteredList)
            }
        }
    }

    fun searchSkins(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            currentSearchInput = name
            val list = favorites.filterListByName(name)
            _favoritesFlow.emit(list)
        }
    }

    fun updateFavoriteSkin(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = favorites.find { it.id == id }?.favorite?.not() ?: false
            favoritesRepository.updateFavoriteSkin(id, favorite)
        }
    }
}
