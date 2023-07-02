package ua.animecraft.core.data.repository

import com.animecraft.core.domain.repository.CategoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ua.animecraft.core.data.mapper.to
import ua.anime.animecraft.data.network.RealtimeSkinsApi
import ua.anime.animecraft.data.network.model.NetworkCategory
import ua.anime.animecraft.domain.repository.CategoryRepository
import ua.anime.animecraft.ui.model.Category
import ua.animecraft.core.data.mapper.toCategory
import ua.animecraft.core.data.mapper.toCategoryList
import ua.animecraft.database.dao.CategoryDao
import ua.animecraft.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

class CategoryRepositoryImpl @Inject constructor(
    private val realtimeSkinsApi: RealtimeSkinsApi,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    private val categoriesMap = mutableMapOf<Int, Category>()

    override suspend fun getCategory(id: Int) = categoryDao.getCategory(id).toCategory()

    override suspend fun updateLocalCategoriesFromNetwork() {
        val categories = realtimeSkinsApi.getAllCategories().map(NetworkCategory::to)
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
