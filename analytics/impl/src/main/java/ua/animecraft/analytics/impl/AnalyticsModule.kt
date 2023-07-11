package ua.animecraft.analytics.impl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.animecraft.analytics.api.AnalyticsApi
import ua.animecraft.analytics.impl.AnalyticsApiImpl

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/18/2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface AnalyticsModule {

    @Binds
    @Singleton
    fun bindAnalytics(impl: AnalyticsApiImpl): AnalyticsApi
}
