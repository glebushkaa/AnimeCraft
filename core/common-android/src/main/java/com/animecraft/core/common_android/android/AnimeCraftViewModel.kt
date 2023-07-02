package com.animecraft.core.common_android.android

import androidx.lifecycle.ViewModel
import com.animecraft.core.log.tag
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import com.animecraft.core.log.error

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

abstract class AnimeCraftViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        error("View model exception -> ${this@AnimeCraftViewModel.tag()}", throwable)
    }
    private val job = SupervisorJob()
    private val context = Dispatchers.Main.immediate + job + coroutineExceptionHandler

    val viewModelScope = CoroutineScope(context)

    override fun onCleared() {
        context.cancel()
        super.onCleared()
    }
}
