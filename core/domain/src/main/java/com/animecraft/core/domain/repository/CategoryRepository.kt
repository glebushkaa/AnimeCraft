package com.animecraft.core.domain.repository

import com.animecraft.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

interface CategoryRepository {

    suspend fun updateLocalCategoriesFromNetwork()

    suspend fun getCategoriesFlow(): Flow<List<Category>>

    suspend fun getCategoryById(id: Int): Category
}
