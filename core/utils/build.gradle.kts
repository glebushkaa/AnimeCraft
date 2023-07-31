import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.lint")
    id("animecraft.android.library")
}

android {
    namespace = "$APPLICATION_ID.core.utils"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
