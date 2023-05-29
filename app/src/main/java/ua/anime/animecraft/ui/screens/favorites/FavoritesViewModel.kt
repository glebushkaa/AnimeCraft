package ua.anime.animecraft.ui.screens.favorites

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.core.common.replaceAllElements
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.ui.extensions.filterListByName
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : AnimeCraftViewModel() {

    private val _favoritesFlow = MutableStateFlow(listOf<Skin>())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    private val favorites = mutableListOf<Skin>()

    private var currentSearchInput: String = ""

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
