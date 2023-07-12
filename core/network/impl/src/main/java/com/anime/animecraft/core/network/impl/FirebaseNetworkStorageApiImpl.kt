package com.anime.animecraft.core.network.impl

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.animecraft.core.network.api.NetworkStorageApi
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class FirebaseNetworkStorageApiImpl @Inject constructor(
    storage: FirebaseStorage
) : NetworkStorageApi {

    private val preview = storage.getReference("preview")
    private val game = storage.getReference("game")

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