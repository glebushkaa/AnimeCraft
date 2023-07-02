package ua.animecraft.database.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Keep
@Entity(tableName = "skin")
data class SkinEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val favorite: Boolean = false,
    val gameImageFileName: String,
    val previewImageFileName: String,
    val categoryId: Int? = null
)
