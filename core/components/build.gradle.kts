import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
    id("animecraft.compose.ui")
}
android {
    namespace = "$APPLICATION_ID.core.components"
}

dependencies {
    implementation(projects.core.theme)
    implementation(projects.core.commonAndroid)
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.play.services.ads)
    implementation(libs.coil.compose)
}
