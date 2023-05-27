package ua.anime.animecraft.ui.model

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

data class Skin(
    val id: Int,
    val name: String,
    val gameImageUrl: String,
    val previewImageUrl: String,
    val favorite: Boolean = false
)
