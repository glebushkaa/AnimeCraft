package ua.animecraft.core.data.repository

import com.animecraft.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ua.animecraft.core.data.mapper.toCategoryEntity
import ua.animecraft.core.data.mapper.toCategory
import ua.animecraft.core.data.mapper.toCategoryList
import ua.animecraft.core.network.api.NetworkDatabaseApi
import ua.animecraft.core.network.api.model.NetworkCategory
import ua.animecraft.database.dao.CategoryDao
import ua.animecraft.model.Category
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

class CategoryRepositoryImpl @Inject constructor(
    private val networkDatabaseApi: NetworkDatabaseApi,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    private val categoriesMap = mutableMapOf<Int, Category>()

    override suspend fun getCategory(id: Int) = categoryDao.getCategory(id).toCategory()

    override suspend fun updateLocalCategoriesFromNetwork() {
        val categories = networkDatabaseApi.getAllCategories().map(NetworkCategory::toCategoryEntity)
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
