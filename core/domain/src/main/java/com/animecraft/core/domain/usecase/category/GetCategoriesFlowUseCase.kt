package com.animecraft.core.domain.usecase.category

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.animecraft.model.Category
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class GetCategoriesFlowUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Flow<List<Category>>>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(dispatchersProvider.io()) {
            categoryRepository.getCategoriesFlow()
        }
    }
}