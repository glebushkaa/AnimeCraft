package ua.anime.animecraft.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.anime.animecraft.data.repository.CategoryRepositoryImpl
import ua.anime.animecraft.data.repository.FavoriteRepositoryImpl
import ua.anime.animecraft.data.repository.SkinsRepositoryImpl
import ua.anime.animecraft.domain.repository.CategoryRepository
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.domain.repository.SkinsRepository

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
}
