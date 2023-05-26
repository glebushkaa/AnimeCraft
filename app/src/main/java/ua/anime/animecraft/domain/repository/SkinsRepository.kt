package ua.anime.animecraft.domain.repository

import ua.anime.animecraft.ui.model.Skin


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

interface SkinsRepository {

    suspend fun getSkins(): List<Skin>

    suspend fun changeSkinFavorite(favorite: Boolean)
}