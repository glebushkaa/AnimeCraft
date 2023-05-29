package ua.anime.animecraft.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Parcelize
data class Skin(
    val id: Int,
    val name: String,
    val gameImageFileName: String,
    val previewImageUrl: String,
    val favorite: Boolean = false
) : Parcelable
