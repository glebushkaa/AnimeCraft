package ua.anime.animecraft.ui.screens.info

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.data.files.SkinFilesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.anime.animecraft.domain.repository.CategoryRepository
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Category
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val categoryRepository: CategoryRepository,
    private val skinsPreferencesHandler: SkinsPreferencesHandler,
    private val skinFilesHandler: SkinFilesHandler
) : AnimeCraftViewModel() {

    private val _skinFlow = MutableStateFlow<Skin?>(null)
    val skinFlow = _skinFlow.asStateFlow()

    private val _categoryFlow = MutableStateFlow<String?>(null)
    val categoryFlow = _categoryFlow.asStateFlow()

    val isDownloadDialogDisabled
        get() = skinsPreferencesHandler.getBoolean(IS_DOWNLOAD_DIALOG_DISABLED) ?: false

    fun getCategoryName(id: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val name = categoryRepository.getCategory(id).name
            _categoryFlow.emit(name)
        }
    }

    fun saveGameSkinImage() {
        val gameImageFileName = _skinFlow.value?.gameImageFileName ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            skinFilesHandler.saveSkinToGallery(gameImageFileName)
        } else {
            skinFilesHandler.saveSkinToMinecraft(gameImageFileName)
        }
    }

    fun disableDownloadDialogOpen() {
        skinsPreferencesHandler.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, true)
    }

    fun loadSkin(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            skinsRepository.getSkinFlow(id).collect {
                _skinFlow.emit(it)
            }
        }
    }

    fun updateFavoriteSkin() {
        viewModelScope.launch(Dispatchers.IO) {
            val skin = _skinFlow.value ?: return@launch
            favoritesRepository.updateFavoriteSkin(skin.id, skin.favorite.not())
        }
    }
}
