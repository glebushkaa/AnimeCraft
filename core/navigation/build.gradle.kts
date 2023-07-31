import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    id("animecraft.compose.ui")
}

android {
    namespace = "$APPLICATION_ID.core.navigation"
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.feature.info)
    implementation(projects.feature.splash)
    implementation(projects.feature.favorites)
    implementation(projects.feature.downloadSkin)
    implementation(projects.feature.language)
    implementation(projects.feature.report)
    implementation(projects.feature.settings)
    implementation(projects.feature.rating)

    implementation(projects.core.theme)
    implementation(projects.core.common)

    implementation(libs.compose.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.compose.accomponist.navigation.animation)
}
