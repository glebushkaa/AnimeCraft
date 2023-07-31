import com.android.build.gradle.BaseExtension

@Suppress("UnstableApiUsage")
configure<BaseExtension> {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.4.7"
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    "implementation"(composeBom)
    "implementation"(libs.compose.ui)
    "implementation"(libs.compose.ui.tooling)
    "implementation"(libs.compose.ui.tooling.preview)
    "implementation"(libs.compose.ui.graphics)
    "implementation"(libs.compose.material)
    "implementation"(libs.material)
    "implementation"(libs.androidx.activity.compose)
}