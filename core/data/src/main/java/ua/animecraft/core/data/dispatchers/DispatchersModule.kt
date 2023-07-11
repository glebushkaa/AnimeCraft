package com.assistant.core.data.dispatchers

import com.assistant.core.domain.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/22/2023
 */

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface DispatchersModule {

    @Binds
    @Singleton
    fun bindDispatchersProvider(provider: DispatchersProviderImpl): DispatchersProvider
}
