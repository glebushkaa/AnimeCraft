package com.animecraft.core.domain.usecase.skin

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.skin.GetCategoryNameByIdUseCase.Params
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class GetCategoryNameByIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<String, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            categoryRepository.getCategoryById(params.id).name
        }
    }

    data class Params(
        val id: Int
    ) : UseCase.Params
}
