package ua.animecraft.model

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

data class Skin(
    val id: Int,
    val name: String,
    val gameImageFileName: String,
    val previewImageUrl: String,
    val categoryId: Int? = null,
    val favorite: Boolean = false
)
