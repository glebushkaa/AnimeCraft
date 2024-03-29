package com.anime.animecraft.database.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

@Keep
@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String
)
