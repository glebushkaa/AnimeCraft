package com.animecraft.core.network.api


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

interface NetworkStorageApi {

    suspend fun getPreviewImageUrl(id: Int): String

    suspend fun getGameImageUrl(id: Int): String
}