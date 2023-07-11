package ua.animecraft.core.network.api

import ua.animecraft.core.network.api.model.NetworkCategory
import ua.animecraft.core.network.api.model.NetworkSkin


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

interface NetworkDatabaseApi {

    suspend fun getAllCategories(): List<NetworkCategory>

    suspend fun getAllSkins(): List<NetworkSkin>
}