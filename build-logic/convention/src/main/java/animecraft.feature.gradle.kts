plugins {
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("animecraft.compose.ui")
}

dependencies {
    "implementation"(project(":core:common-android"))
    "implementation"(project(":core:domain"))
    "implementation"(project(":core:log"))

    "implementation"(libs.compose.navigation)
    "implementation"(libs.compose.accomponist.navigation.animation)

    "implementation"(libs.hilt.android)
    "implementation"(libs.hilt.navigation.compose)
    "kapt"(libs.hilt.compiler)
}