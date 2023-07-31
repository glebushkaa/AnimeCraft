package com.animecraft.core.domain.usecase.core

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/22/2023
 */

abstract class ResultNoneParamsUseCase<out Type : Any>(
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Type, Nothing>(useCaseLogger) {

    abstract suspend operator fun invoke(): Result<Type>
    override suspend fun invoke(params: Nothing) = invoke()
}
