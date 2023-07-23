package com.animecraft.core.domain.repository

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/22/2023
 */

interface FilesRepository {

    fun saveGameSkinToUser(fileName: String): Result<Unit>

    fun checkGameSkinExist(fileName: String): Boolean

    suspend fun saveSkinFromStorage(id: Int): String
}
