import com.android.build.gradle.BaseExtension
import com.animecraft.build.logic.convention.AnimeCraftConfig
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
}

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties: Properties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

configure<BaseExtension> {
    commonAndroid(project)

    namespace = AnimeCraftConfig.APPLICATION_ID
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = AnimeCraftConfig.APPLICATION_ID
        versionCode = AnimeCraftConfig.VERSION_CODE
        versionName = AnimeCraftConfig.VERSION_NAME
    }

    signingConfigs {
        create("staging") {
            storeFile = file(keystoreProperties.getProperty("KEY_STORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEY_STORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
        }
        create("release") {
            storeFile = file(keystoreProperties.getProperty("KEY_STORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEY_STORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".dev.debug"
        }
        staging {
            applicationIdSuffix = ".dev.staging"
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("staging")
        }
        release {
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
