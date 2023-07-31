package com.anime.animecraft.core.network.impl

import com.animecraft.core.network.api.NetworkStorageApi
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class FirebaseNetworkStorageApiImpl @Inject constructor(
    storage: FirebaseStorage
) : NetworkStorageApi {

    private val preview = storage.getReference("preview")
    private val game = storage.getReference("game")

    override suspend fun getGameImageBytes(id: Int): ByteArray {
        return game.child("$id.png")
            .getBytes(Long.MAX_VALUE)
            .await()
    }

    override suspend fun getPreviewImageUrl(id: Int): String {
        return preview.child("$id.png")
            .downloadUrl
            .await()
            .toString()
    }

    override suspend fun getGameImageUrl(id: Int): String {
        return game.child("$id.png")
            .downloadUrl
            .await()
            .toString()
    }
}
