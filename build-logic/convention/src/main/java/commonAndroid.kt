import com.android.build.gradle.BaseExtension
import com.animecraft.build.logic.convention.AnimeCraftConfig
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/21/2023
 */

fun BaseExtension.commonAndroid(project: Project) {
    configureDefaultConfiguration()
    configureBuildTypes()
    configureCompileOptions()

    project.suppressOptIn()
}

fun BaseExtension.configureDefaultConfiguration() {
    compileSdkVersion(AnimeCraftConfig.COMPILE_SDK_VERSION)

    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = AnimeCraftConfig.MIN_SDK_VERSION
        targetSdk = AnimeCraftConfig.TARGET_SDK_VERSION

        consumerProguardFiles("consumer-rules.pro")

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
    }
}

fun BaseExtension.configureBuildTypes() {
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        staging {
            isDebuggable = false
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun BaseExtension.configureCompileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun Project.suppressOptIn() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                "-Xopt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi"
            )
        }
    }
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.debug(action: BuildTypeT.() -> Unit) {
    maybeCreate("debug").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.staging(action: BuildTypeT.() -> Unit) {
    maybeCreate("staging").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.release(action: BuildTypeT.() -> Unit) {
    maybeCreate("release").action()
}