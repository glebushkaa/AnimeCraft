package com.animecraft.core.android

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

sealed class ResourceEvent<out T : Any>(resource: Resource<T>) : Event<Resource<T>>(resource) {

    object Loading : ResourceEvent<Nothing>(Resource.Loading)
    data class Success<out T : Any>(val data: T) : ResourceEvent<T>(Resource.Success(data))
    data class Error(
        val message: String?,
        val exception: Throwable
    ) : ResourceEvent<Nothing>(Resource.Error(message, exception))
}
