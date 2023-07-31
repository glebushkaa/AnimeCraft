package com.animecraft.core.domain.files

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/2/2023
 */

interface SkinFilesApi {

    fun saveSkinToMinecraft(fileName: String): Result<Unit>

    fun saveSkinToGallery(fileName: String): Result<Unit>

    fun checkGameSkinExist(fileName: String): Boolean

    suspend fun saveFileFromBytes(fileName: String, bytes: ByteArray)
}
