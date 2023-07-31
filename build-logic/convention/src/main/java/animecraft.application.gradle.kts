import com.android.build.gradle.BaseExtension
import com.animecraft.build.logic.convention.AnimeCraftConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
}

configure<BaseExtension> {
    commonAndroid(project)

    namespace = AnimeCraftConfig.APPLICATION_ID
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = AnimeCraftConfig.APPLICATION_ID
        versionCode = AnimeCraftConfig.VERSION_CODE
        versionName = AnimeCraftConfig.VERSION_NAME
    }

    buildTypes {
        staging {
            isShrinkResources = true
        }
        release {
            isShrinkResources = true
        }
    }
}
