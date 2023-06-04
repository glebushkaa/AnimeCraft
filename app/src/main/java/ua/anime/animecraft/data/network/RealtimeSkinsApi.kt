package ua.anime.animecraft.data.network

import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import ua.anime.animecraft.data.network.model.NetworkCategory
import ua.anime.animecraft.data.network.model.NetworkSkin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class RealtimeSkinsApi @Inject constructor(database: FirebaseDatabase) {

    private val skinsRef = database.getReference(SKINS)
    private val categoriesRef = database.getReference(CATEGORIES)

    suspend fun getAllCategories(): List<NetworkCategory> = categoriesRef.get().await().run {
        val categoriesList = mutableListOf<NetworkCategory>()
        children.forEach {
            val category = it.getValue(NetworkCategory::class.java) ?: return@forEach
            categoriesList.add(category)
        }
        return@run categoriesList
    }

    suspend fun getAllSkins(): List<NetworkSkin> = skinsRef.get().await().run {
        val skinsList = mutableListOf<NetworkSkin>()
        children.forEach {
            val skin = it.getValue(NetworkSkin::class.java) ?: return@forEach
            skinsList.add(skin)
        }
        return@run skinsList
    }
}
