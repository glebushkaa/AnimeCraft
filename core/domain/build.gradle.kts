plugins {
    id("animecraft.library")
    id("animecraft.lint")
}

dependencies {
    api(projects.core.model)
    api(projects.core.dataStore.api)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.inject)
}