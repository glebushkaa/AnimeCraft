package com.assistant.core.data.dispatchers

import com.assistant.core.domain.DispatchersProvider
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/22/2023
 */

class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
