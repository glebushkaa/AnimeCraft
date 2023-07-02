package ua.anime.animecraft.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.animecraft.core.data.repository.CategoryRepositoryImpl
import ua.animecraft.core.data.repository.FavoriteRepositoryImpl
import ua.animecraft.core.data.repository.SkinsRepositoryImpl
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.repository.SkinsRepository

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSkinsRepository(repository: ua.animecraft.core.data.repository.SkinsRepositoryImpl): com.animecraft.core.domain.repository.SkinsRepository

    @Singleton
    @Binds
    fun bindFavoritesRepository(repository: ua.animecraft.core.data.repository.FavoriteRepositoryImpl): com.animecraft.core.domain.repository.FavoritesRepository

    @Singleton
    @Binds
    fun bindCategoriesRepository(repository: ua.animecraft.core.data.repository.CategoryRepositoryImpl): com.animecraft.core.domain.repository.CategoryRepository
}
