package ua.anime.animecraft.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Skin
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 *
 * TODO extract Dispatchers to use cases or repository
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository
) : AnimeCraftViewModel() {

    private val _skinsFlow = MutableStateFlow<List<Skin>>(listOf())
    val skinsFlow: StateFlow<List<Skin>> = _skinsFlow

    private val skins = mutableListOf<Skin>()

    fun getAllSkins() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = skinsRepository.getSkins()
            skins.clear()
            skins.addAll(list)
            _skinsFlow.emit(skins)
        }
    }

    fun searchSkins(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val list = skins.filterListByName(name)
            _skinsFlow.emit(list)
        }
    }

    private fun List<Skin>.filterListByName(name: String) = filter {
        it.name.startsWith(name) || it.name.contains(name)
    }
}