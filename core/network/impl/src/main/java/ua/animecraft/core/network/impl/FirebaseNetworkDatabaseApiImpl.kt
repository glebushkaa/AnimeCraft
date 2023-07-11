package ua.animecraft.core.network.impl

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import ua.animecraft.core.network.api.NetworkDatabaseApi
import ua.animecraft.core.network.api.model.NetworkCategory
import ua.animecraft.core.network.api.model.NetworkSkin
import ua.animecraft.core.network.impl.mapper.toNetworkCategory
import ua.animecraft.core.network.impl.mapper.toNetworkSkin
import ua.animecraft.core.network.impl.model.FirebaseCategory
import ua.animecraft.core.network.impl.model.FirebaseSkin
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class FirebaseNetworkDatabaseApiImpl @Inject constructor(
    database: FirebaseDatabase
) : NetworkDatabaseApi {

    private val skinsRef = database.getReference("skins")
    private val categoriesRef = database.getReference("categories")

    override suspend fun getAllCategories(): List<NetworkCategory> {
        val dataSnapshot = categoriesRef.get().await()
        val categoriesList = mutableListOf<FirebaseCategory>()
        dataSnapshot.children.forEach {
            val category = it.getValue(FirebaseCategory::class.java) ?: return@forEach
            categoriesList.add(category)
        }
        return categoriesList.map { it.toNetworkCategory() }
    }

    override suspend fun getAllSkins(): List<NetworkSkin> {
        val dataSnapshot = skinsRef.get().await()
        val skinsList = mutableListOf<FirebaseSkin>()
        dataSnapshot.children.forEach {
            val skin = it.getValue(FirebaseSkin::class.java) ?: return@forEach
            skinsList.add(skin)
        }
        return skinsList.map { it.toNetworkSkin() }
    }
}