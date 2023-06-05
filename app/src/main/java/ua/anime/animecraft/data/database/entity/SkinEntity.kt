package ua.anime.animecraft.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.anime.animecraft.data.database.SKIN_ENTITY

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Entity(tableName = SKIN_ENTITY)
data class SkinEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val favorite: Boolean = false,
    val gameImageFileName: String,
    val previewImageFileName: String,
    val categoryId: Int? = null
)
