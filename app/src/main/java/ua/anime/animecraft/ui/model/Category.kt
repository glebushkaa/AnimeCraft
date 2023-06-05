package ua.anime.animecraft.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

@Parcelize
data class Category(
    val id: Int,
    val name: String
) : Parcelable
