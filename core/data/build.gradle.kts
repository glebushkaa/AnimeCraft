import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
    alias(libs.plugins.hilt)
    kotlin("kapt")
}
android {
    namespace = "$APPLICATION_ID.core.data"
}

dependencies {
    implementation(projects.core.log)
    implementation(projects.core.domain)
    implementation(projects.core.network.api)
    implementation(projects.core.database)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
