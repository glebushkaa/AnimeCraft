package ua.anime.animecraft.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.anime.animecraft.data.database.SKINS_DATABASE_NAME
import ua.anime.animecraft.data.database.database.SkinsDatabase
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SkinsDatabase =
        Room.databaseBuilder(context, SkinsDatabase::class.java, SKINS_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSkinsDao(database: SkinsDatabase) = database.skinsDao()

    @Provides
    @Singleton
    fun provideFavoritesDao(database: SkinsDatabase) = database.favoriteDao()
}
