package com.animecraft.core.domain

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/22/2023
 */

interface DispatchersProvider {



    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun default(): CoroutineDispatcher
}
