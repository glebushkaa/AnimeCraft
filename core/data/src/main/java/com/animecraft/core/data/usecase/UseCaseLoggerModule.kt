package com.animecraft.core.data.usecase

import com.animecraft.core.domain.usecase.core.UseCaseLogger
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
interface UseCaseLoggerModule {

    @Binds
    @Singleton
    fun provideUseCaseLogger(useCaseLoggerImpl: UseCaseLoggerImpl): UseCaseLogger
}
