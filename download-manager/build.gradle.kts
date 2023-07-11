import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "$APPLICATION_ID.core.download.manager"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.log)

    implementation(libs.work.manager)

    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    kapt(libs.hilt.compiler)
}