package ua.anime.animecraft.ui.screens.main

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.animecraft.core.common_android.android.AnimeCraftViewModel
import com.animecraft.core.common_android.android.Event
import ua.anime.animecraft.core.common.replaceAllElements
import ua.anime.animecraft.data.files.SkinFilesApiImpl
import ua.anime.animecraft.data.preferences.SkinsPreferencesApiImpl
import ua.anime.animecraft.data.preferences.SkinsPreferencesApiImpl.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesApiImpl.Companion.IS_RATE_COMPLETED
import ua.anime.animecraft.data.preferences.SkinsPreferencesApiImpl.Companion.IS_RATE_DIALOG_DISABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesApiImpl.Companion.TIMES_APP_OPENED
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.extensions.filterListByCategoryId
import ua.anime.animecraft.ui.extensions.filterListByName
import ua.anime.animecraft.ui.model.Category
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val skinsRepository: com.animecraft.core.domain.repository.SkinsRepository,
    private val favoritesRepository: com.animecraft.core.domain.repository.FavoritesRepository,
    private val categoryRepository: com.animecraft.core.domain.repository.CategoryRepository,
    private val skinFilesApiImpl: SkinFilesApiImpl,
    private val skinsPreferencesApiImpl: SkinsPreferencesApiImpl
) : com.animecraft.core.common_android.android.AnimeCraftViewModel() {

    private val _skinsFlow = MutableStateFlow<List<Skin>>(listOf())
    val skinsFlow = _skinsFlow.asStateFlow()

    private val _categoriesFlow = MutableStateFlow<List<Category>>(listOf())
    val categoriesFlow = _categoriesFlow.asStateFlow()

    private val _downloadFlow = MutableStateFlow<com.animecraft.core.common_android.android.Event<Boolean?>>(
        com.animecraft.core.common_android.android.Event(null)
    )
    val downloadFlow = _downloadFlow.asStateFlow()

    private var currentSearchInput: String = ""
    var selectedCategory: Category? = null
        private set

    private val skins = mutableListOf<Skin>()
    private val categories = mutableListOf<Category>()

    var areSkinsLoaded: Boolean = false
        private set

    val isDownloadDialogDisabled: Boolean
        get() = skinsPreferencesApiImpl.getBoolean(IS_DOWNLOAD_DIALOG_DISABLED) ?: false

    private val isRateDialogDisabled: Boolean
        get() = skinsPreferencesApiImpl.getBoolean(IS_RATE_DIALOG_DISABLED) ?: false
    private val isRateCompleted: Boolean
        get() = skinsPreferencesApiImpl.getBoolean(IS_RATE_COMPLETED) ?: false
    private val timesAppOpened: Int
        get() = skinsPreferencesApiImpl.getInt(TIMES_APP_OPENED) ?: 0

    private var isDialogWasShown = false

    init {
        getAllCategories()
        getAllSkins()
    }

    fun setDialogWasShown() {
        isDialogWasShown = true
    }

    fun setRateDialogCompleted() {
        skinsPreferencesApiImpl.putBoolean(IS_RATE_COMPLETED, true)
    }

    fun selectCategory(category: Category?) = viewModelScope.launch(Dispatchers.Default) {
        selectedCategory = category
        _skinsFlow.emit(getFilteredList())
    }

    fun shouldRateDialogBeShown(): Boolean {
        return !isRateDialogDisabled &&
            timesAppOpened % EVERY_THIRD_OPEN == 0 &&
            timesAppOpened >= EVERY_THIRD_OPEN &&
            !isRateCompleted &&
            !isDialogWasShown
    }

    fun disableRateDialog() {
        skinsPreferencesApiImpl.putBoolean(IS_RATE_DIALOG_DISABLED, true)
    }

    fun saveGameSkinImage(id: Int) = viewModelScope.launch(Dispatchers.Default) {
        val gameImageFileName = skins.find { it.id == id }?.gameImageFileName ?: return@launch
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            skinFilesApiImpl.saveSkinToGallery(gameImageFileName)
        } else {
            val result = skinFilesApiImpl.saveSkinToMinecraft(gameImageFileName)
            if (!result.isSuccess) skinFilesApiImpl.saveSkinToGallery(gameImageFileName) else result
        }
        _downloadFlow.emit(com.animecraft.core.common_android.android.Event(result.isSuccess))
    }

    fun disableDownloadDialogOpen() {
        skinsPreferencesApiImpl.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, true)
    }

    private fun getAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.getCategoriesFlow().collect {
            categories.replaceAllElements(it)
            _categoriesFlow.emit(categories)
        }
    }

    private fun getFilteredList() = if (selectedCategory == null) {
        skins.filterListByName(currentSearchInput)
    } else {
        skins.filterListByCategoryId(selectedCategory!!.id).filterListByName(currentSearchInput)
    }

    private fun getAllSkins() = viewModelScope.launch(Dispatchers.IO) {
        skinsRepository.getSkinsFlow().collect {
            areSkinsLoaded = true
            skins.replaceAllElements(it)
            _skinsFlow.emit(getFilteredList())
        }
    }

    fun searchSkins(name: String) = viewModelScope.launch(Dispatchers.Default) {
        currentSearchInput = name
        _skinsFlow.emit(getFilteredList())
    }

    fun updateFavoriteSkin(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val favorite = skins.find { it.id == id }?.favorite?.not() ?: false
        favoritesRepository.updateSkinFavoriteState(id, favorite)
    }

    private companion object {
        private const val EVERY_THIRD_OPEN = 3
    }
}
