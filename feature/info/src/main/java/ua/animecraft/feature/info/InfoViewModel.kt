package ua.animecraft.feature.info

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.android.Event
import ua.animecraft.core.files.SkinFilesApiImpl
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val checkDownloadDialogDisabledUseCase: com.animecraft.core.domain.usecase.prefs.CheckDownloadDialogDisabledUseCase,
    private val skinsRepository: com.animecraft.core.domain.repository.SkinsRepository,
    private val favoritesRepository: com.animecraft.core.domain.repository.FavoritesRepository,
    private val categoryRepository: com.animecraft.core.domain.repository.CategoryRepository,
    private val skinsPreferencesApiImpl: SkinsPreferencesApiImpl,
    private val skinFilesApiImpl: ua.animecraft.core.files.SkinFilesApiImpl
) : AnimeCraftViewModel() {

    private val _skinFlow = MutableStateFlow<Skin?>(null)
    val skinFlow = _skinFlow.asStateFlow()

    private val _categoryFlow = MutableStateFlow<String?>(null)
    val categoryFlow = _categoryFlow.asStateFlow()

    private val _downloadFlow = MutableStateFlow<Event<Boolean?>>(
        Event(null)
    )
    val downloadFlow = _downloadFlow.asStateFlow()

    val isDownloadDialogDisabled
        get() = skinsPreferencesApiImpl.getBoolean(IS_DOWNLOAD_DIALOG_DISABLED) ?: false

    private fun getCategoryName(id: Int) = viewModelScope.launch(Dispatchers.Default) {
        val name = categoryRepository.getCategory(id).name
        _categoryFlow.emit(name)
    }

    fun saveGameSkinImage() = viewModelScope.launch {
        val gameImageFileName = _skinFlow.value?.gameImageFileName ?: return@launch
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            skinFilesApiImpl.saveSkinToGallery(gameImageFileName)
        } else {
            val result = skinFilesApiImpl.saveSkinToMinecraft(gameImageFileName)
            if (!result.isSuccess) skinFilesApiImpl.saveSkinToGallery(gameImageFileName) else result
        }
        _downloadFlow.emit(Event(result.isSuccess))
    }

    fun disableDownloadDialogOpen() {
        skinsPreferencesApiImpl.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, true)
    }

    fun loadSkin(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        skinsRepository.getSkinFlow(id).collect {
            it.categoryId?.let { categoryId -> getCategoryName(categoryId) }
            _skinFlow.emit(it)
        }
    }

    fun updateFavoriteSkin() = viewModelScope.launch(Dispatchers.IO) {
        val skin = _skinFlow.value ?: return@launch
        favoritesRepository.updateSkinFavoriteState(skin.id, skin.favorite.not())
    }
}
