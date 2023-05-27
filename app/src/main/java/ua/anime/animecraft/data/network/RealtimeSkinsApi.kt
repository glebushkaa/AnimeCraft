package ua.anime.animecraft.data.network

import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import ua.anime.animecraft.data.network.model.NetworkSkin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class RealtimeSkinsApi @Inject constructor(database: FirebaseDatabase) {

    private val skinsRef = database.getReference(SKINS)

    suspend fun getAllSkins(): List<NetworkSkin> = skinsRef.get().await().run {
        val skinsList = mutableListOf<NetworkSkin>()
        children.forEach {
            val skin = it.getValue(NetworkSkin::class.java) ?: return@forEach
            skinsList.add(skin)
        }
        return@run skinsList
    }
}
