import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    kotlin("kapt")
}
android {
    namespace = "$APPLICATION_ID.core.network.impl"
}

dependencies {
    implementation(projects.core.log)
    implementation(projects.core.domain)
    implementation(projects.core.network.api)

    implementation(libs.firebase.realtime.database.ktx)
    implementation(libs.firebase.storage.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
