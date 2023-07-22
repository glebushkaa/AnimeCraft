package com.animecraft.core.data

import com.animecraft.core.data.repository.CategoryRepositoryImpl
import com.animecraft.core.data.repository.FavoriteRepositoryImpl
import com.animecraft.core.data.repository.FilesRepositoryImpl
import com.animecraft.core.data.repository.SkinsRepositoryImpl
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.repository.FilesRepository
import com.animecraft.core.domain.repository.SkinsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSkinsRepository(repository: SkinsRepositoryImpl): SkinsRepository

    @Singleton
    @Binds
    fun bindFavoritesRepository(repository: FavoriteRepositoryImpl): FavoritesRepository

    @Singleton
    @Binds
    fun bindCategoriesRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @Singleton
    @Binds
    fun bindFilesRepository(repository: FilesRepositoryImpl): FilesRepository
}
