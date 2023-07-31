package com.anime.animecraft.core.network.impl

import com.anime.animecraft.core.network.impl.mapper.toNetworkCategory
import com.anime.animecraft.core.network.impl.mapper.toNetworkSkin
import com.anime.animecraft.core.network.impl.model.FirebaseCategory
import com.anime.animecraft.core.network.impl.model.FirebaseSkin
import com.animecraft.core.network.api.NetworkDatabaseApi
import com.animecraft.core.network.api.model.NetworkCategory
import com.animecraft.core.network.api.model.NetworkSkin
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class FirebaseNetworkDatabaseApiImpl @Inject constructor(
    database: FirebaseDatabase
) : NetworkDatabaseApi {

    private val skinsRef = database.getReference("skins")
    private val categoriesRef = database.getReference("categories")
    private val ratingsRef = database.getReference("ratings")

    override suspend fun sendRating(rating: Int) {
        val child = ratingsRef.child(rating.toString())
        val value = child.get().await().value as? Long ?: 0
        child.setValue(value + 1).await()
    }

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
