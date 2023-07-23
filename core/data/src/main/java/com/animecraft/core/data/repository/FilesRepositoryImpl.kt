package com.animecraft.core.data.repository

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import com.animecraft.core.domain.files.SkinFilesApi
import com.animecraft.core.domain.repository.FilesRepository
import com.animecraft.core.network.api.NetworkStorageApi
import java.util.UUID
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/22/2023
 */

class FilesRepositoryImpl @Inject constructor(
    private val skinFilesApi: SkinFilesApi,
    private val networkStorageApi: NetworkStorageApi
) : FilesRepository {

    override fun saveGameSkinToUser(fileName: String): Result<Unit> {
        val ableToSaveInGame = SDK_INT <= Build.VERSION_CODES.Q
        return if (ableToSaveInGame) {
            val result = skinFilesApi.saveSkinToMinecraft(fileName)
            if (!result.isSuccess) skinFilesApi.saveSkinToGallery(fileName) else result
        } else {
            skinFilesApi.saveSkinToGallery(fileName)
        }
    }

    override fun checkGameSkinExist(fileName: String): Boolean {
        return skinFilesApi.checkGameSkinExist(fileName)
    }

    override suspend fun saveSkinFromStorage(id: Int): String {
        val fileName = "${UUID.randomUUID()}.png"
        val bytes = networkStorageApi.getGameImageBytes(id)
        skinFilesApi.saveFileFromBytes(fileName, bytes)
        return fileName
    }
}
