import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
    id("animecraft.compose.ui")
}
android {
    namespace = "$APPLICATION_ID.core.common.android"
}

dependencies {
    implementation(libs.play.core)
    implementation(projects.core.log)
    implementation(libs.androidx.appcompat)
}
