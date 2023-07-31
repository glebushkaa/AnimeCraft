package com.anime.animecraft.core.android.extensions

import com.anime.animecraft.core.android.Resource

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

inline fun <T : Any> handleResource(
    resource: Resource<T>,
    crossinline onLoading: () -> Unit,
    crossinline onError: (String, Throwable) -> Unit,
    crossinline onHideLoading: () -> Unit,
    crossinline onSuccess: (T) -> Unit
) = when (resource) {
    Resource.Loading -> onLoading()
    is Resource.Error -> {
        onError(resource.message ?: "", resource.exception)
        onHideLoading()
    }

    is Resource.Success -> {
        onSuccess(resource.data)
        onHideLoading()
    }
}

inline fun handleUnitResource(
    resource: Resource<Unit>,
    crossinline onLoading: () -> Unit,
    crossinline onError: (String, Throwable) -> Unit,
    crossinline onHideLoading: () -> Unit,
    crossinline onSuccess: () -> Unit
) = when (resource) {
    Resource.Loading -> onLoading()
    is Resource.Error -> {
        onError(resource.message ?: "", resource.exception)
        onHideLoading()
    }

    is Resource.Success -> {
        onSuccess()
        onHideLoading()
    }
}
