package ua.anime.animecraft.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ua.anime.animecraft.data.database.dao.CategoryDao
import ua.anime.animecraft.data.database.entity.CategoryEntity
import ua.anime.animecraft.data.mapper.to
import ua.anime.animecraft.data.network.RealtimeSkinsApi
import ua.anime.animecraft.data.network.model.NetworkCategory
import ua.anime.animecraft.domain.repository.CategoryRepository
import ua.anime.animecraft.ui.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

class CategoryRepositoryImpl @Inject constructor(
    private val realtimeSkinsApi: RealtimeSkinsApi,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    private val categoriesMap = mutableMapOf<Int, Category>()

    override suspend fun getCategory(id: Int) = categoryDao.getCategory(id).to()

    override suspend fun updateLocalCategoriesFromNetwork() {
        val categories = realtimeSkinsApi.getAllCategories().map(NetworkCategory::to)
        categoryDao.insert(categories)
    }

    override suspend fun getCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getCategories().map(List<CategoryEntity>::to).onEach { categories ->
            categories.forEach { category ->
                categoriesMap[category.id] = category
            }
        }
    }
}
