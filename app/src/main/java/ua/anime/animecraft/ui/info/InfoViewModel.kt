package ua.anime.animecraft.ui.info

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository,
    private val favoritesRepository: FavoritesRepository
) : AnimeCraftViewModel() {

    private val _skinFlow = MutableStateFlow<Skin?>(null)
    val skinFlow = _skinFlow.asStateFlow()

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
