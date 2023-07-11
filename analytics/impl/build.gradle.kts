import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "$APPLICATION_ID.analytics.impl"
}

dependencies {
    implementation(projects.analytics.api)
    implementation(projects.core.log)

    implementation(libs.firebase.analytics.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}