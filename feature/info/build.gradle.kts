import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    id("animecraft.feature")
}

android {
    namespace = "$APPLICATION_ID.feature.info"
}

dependencies {
    implementation(projects.core.components)

    implementation(libs.coil.compose)
}
