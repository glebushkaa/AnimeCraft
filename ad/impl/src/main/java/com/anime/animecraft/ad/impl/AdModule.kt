package com.anime.animecraft.ad.impl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.animecraft.ad.api.AppOpenAdApi
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface AdModule {

    @Binds
    @Singleton
    fun provideAppOpenAd(
        appOpenAdImpl: AppOpenAdApiImpl
    ): AppOpenAdApi
}
