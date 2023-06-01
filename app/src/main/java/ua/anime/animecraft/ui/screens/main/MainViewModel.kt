package ua.anime.animecraft.ui.screens.main

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.core.common.replaceAllElements
import ua.anime.animecraft.data.files.SkinFilesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_RATE_COMPLETED
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_RATE_DIALOG_DISABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.TIMES_APP_OPENED
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.extensions.filterListByName
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 *
 * TODO extract Dispatchers to use cases or repository
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val skinFilesHandler: SkinFilesHandler,
    private val skinsPreferencesHandler: SkinsPreferencesHandler
) : AnimeCraftViewModel() {

    private val _skinsFlow = MutableStateFlow<List<Skin>>(listOf())
    val skinsFlow = _skinsFlow.asStateFlow()

    private var currentSearchInput: String = ""

    private val skins = mutableListOf<Skin>()

    var areSkinsLoaded: Boolean = false
        private set

    val isDownloadDialogDisabled = skinsPreferencesHandler.getBoolean(
        IS_DOWNLOAD_DIALOG_DISABLED
    ) ?: false

    private val isRateDialogDisabled: Boolean
        get() = skinsPreferencesHandler.getBoolean(IS_RATE_DIALOG_DISABLED) ?: false
    private val isRateCompleted: Boolean
        get() = skinsPreferencesHandler.getBoolean(IS_RATE_COMPLETED) ?: false
    private val timesAppOpened: Int
        get() = skinsPreferencesHandler.getInt(TIMES_APP_OPENED) ?: 0

    private var isDialogWasShown = false

    fun setDialogWasShown() {
        isDialogWasShown = true
    }

    fun setRateDialogCompleted() {
        skinsPreferencesHandler.putBoolean(IS_RATE_COMPLETED, true)
    }

    fun shouldRateDialogBeShown(): Boolean {
        return !isRateDialogDisabled &&
            timesAppOpened % EVERY_THIRD_OPEN == 0 &&
            timesAppOpened >= EVERY_THIRD_OPEN &&
            !isRateCompleted &&
            !isDialogWasShown
    }

    fun disableRateDialog() {
        skinsPreferencesHandler.putBoolean(IS_RATE_DIALOG_DISABLED, true)
    }

    fun saveGameSkinImage(id: Int) {
        val gameImageFileName = skins.find { it.id == id }?.gameImageFileName ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            skinFilesHandler.saveSkinToGallery(gameImageFileName)
        } else {
            skinFilesHandler.saveSkinToMinecraft(gameImageFileName)
        }
    }

    fun disableDownloadDialogOpen() {
        skinsPreferencesHandler.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, true)
    }

    fun getAllSkins() {
        viewModelScope.launch(Dispatchers.IO) {
            skinsRepository.getSkinsFlow().collect {
                areSkinsLoaded = true
                skins.replaceAllElements(it)
                val filteredList = skins.filterListByName(currentSearchInput)
                _skinsFlow.emit(filteredList)
            }
        }
    }

    fun searchSkins(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            currentSearchInput = name
            val list = skins.filterListByName(name)
            _skinsFlow.emit(list)
        }
    }

    fun updateFavoriteSkin(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = skins.find { it.id == id }?.favorite?.not() ?: false
            favoritesRepository.updateFavoriteSkin(id, favorite)
        }
    }

    private companion object {
        const val EVERY_THIRD_OPEN = 3
    }
}
