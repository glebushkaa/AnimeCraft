import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    kotlin("kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "$APPLICATION_ID.core.database"
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
