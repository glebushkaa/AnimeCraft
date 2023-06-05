package ua.anime.animecraft.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.anime.animecraft.ui.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

interface CategoryRepository {

    suspend fun updateLocalCategoriesFromNetwork()

    suspend fun getCategoriesFlow(): Flow<List<Category>>

    suspend fun getCategory(id: Int): Category
}
