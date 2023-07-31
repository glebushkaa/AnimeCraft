plugins {
    id("animecraft.library")
    id("animecraft.lint")
}

dependencies {
    api(projects.core.model)
    api(projects.core.dataStore.api)
    api(projects.ad.api)
    api(projects.analytics.api)
    api(projects.core.network.api)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.inject)
}
