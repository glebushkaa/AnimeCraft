package ua.anime.animecraft.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.anime.animecraft.data.files.SkinFilesApiImpl
import ua.anime.animecraft.domain.files.SkinFilesApi
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/2/2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface FilesModule {

    @Binds
    @Singleton
    fun provideSkinFilesApi(
        skinFilesApiImpl: SkinFilesApiImpl
    ): SkinFilesApi
}