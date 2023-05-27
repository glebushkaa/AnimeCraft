package ua.anime.animecraft.core.android

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val message: String?, val exception: Throwable) : Resource<Nothing>()
}
