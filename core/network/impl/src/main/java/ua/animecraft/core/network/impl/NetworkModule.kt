package ua.animecraft.core.network.impl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.animecraft.core.network.api.NetworkDatabaseApi
import ua.animecraft.core.network.api.NetworkStorageApi
import javax.inject.Singleton


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun bindFirebaseNetworkDatabaseApi(
        impl: FirebaseNetworkDatabaseApiImpl
    ): NetworkDatabaseApi

    @Binds
    @Singleton
    fun bindFirebaseNetworkStorageApi(
        impl: FirebaseNetworkStorageApiImpl
    ): NetworkStorageApi
}