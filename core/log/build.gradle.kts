import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
}

android {
    namespace = "$APPLICATION_ID.core.log"
}

dependencies {
    implementation(libs.timber)
}
