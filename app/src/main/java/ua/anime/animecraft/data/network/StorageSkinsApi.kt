package ua.anime.animecraft.data.network

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Singleton
class StorageSkinsApi @Inject constructor(
    storage: FirebaseStorage
) {

    private val preview = storage.getReference(PREVIEW)
    private val game = storage.getReference(GAME)

    suspend fun getPreviewImageUrl(id: Int) = preview.child("$id.png")
        .downloadUrl
        .await()
        .toString()

    suspend fun getGameImageUrl(id: Int) = game.child("$id.png")
        .downloadUrl
        .await()
        .toString()
}