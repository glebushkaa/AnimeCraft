package ua.anime.animecraft.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.anime.animecraft.data.repository.SkinsRepositoryImpl
import ua.anime.animecraft.domain.repository.SkinsRepository
import javax.inject.Singleton


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSkinsRepository(skinsRepositoryImpl: SkinsRepositoryImpl): SkinsRepository
}