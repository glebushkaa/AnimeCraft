package com.animecraft.core.data.repository

import com.anime.animecraft.database.dao.CategoryDao
import com.animecraft.core.data.mapper.toCategory
import com.animecraft.core.data.mapper.toCategoryEntity
import com.animecraft.core.data.mapper.toCategoryList
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.network.api.NetworkDatabaseApi
import com.animecraft.core.network.api.model.NetworkCategory
import com.animecraft.model.Category
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

class CategoryRepositoryImpl @Inject constructor(
    private val networkDatabaseApi: NetworkDatabaseApi,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    private val categoriesMap = mutableMapOf<Int, Category>()

    override suspend fun getCategoryById(id: Int) = categoryDao.getCategory(id).toCategory()

    override suspend fun updateLocalCategoriesFromNetwork() {
        val categories = networkDatabaseApi.getAllCategories().map(
            NetworkCategory::toCategoryEntity
        )
        categoryDao.insert(categories)
    }

    override suspend fun getCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getCategories().map {
            it.toCategoryList()
        }.onEach { categories ->
            categories.forEach { category ->
                categoriesMap[category.id] = category
            }
        }
    }
}
