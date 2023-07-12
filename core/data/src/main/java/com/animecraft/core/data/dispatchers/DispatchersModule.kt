package com.animecraft.core.data.dispatchers

import com.animecraft.core.domain.DispatchersProvider
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
interface DispatchersModule {

    @Binds
    @Singleton
    fun bindDispatchersProvider(
        provider: DispatchersProviderImpl
    ): DispatchersProvider
}
