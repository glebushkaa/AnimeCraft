import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
    alias(libs.plugins.hilt)
    kotlin("kapt")
}
android {
    namespace = "$APPLICATION_ID.core.data.store.impl"
}

dependencies {
    implementation(projects.core.dataStore.api)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.androidx.data.store)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
