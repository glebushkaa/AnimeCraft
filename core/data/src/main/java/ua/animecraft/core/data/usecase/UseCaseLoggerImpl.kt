package ua.animecraft.core.data.usecase

import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.log.error
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/22/2023
 */

class UseCaseLoggerImpl @Inject constructor() : UseCaseLogger {

    override fun logException(tag: String, throwable: Throwable) {
        error(tag, throwable)
    }
}
