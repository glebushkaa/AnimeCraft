@file:Suppress("FunctionName", "UnstableApiUsage")

plugins {
    id("animecraft.lint")
    id("animecraft.application")
    id("animecraft.feature")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    bundle {
        language.enableSplit = false
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.commonAndroid)
    implementation(projects.core.navigation)
    implementation(projects.core.network)
    implementation(projects.core.preferences)
    implementation(projects.core.files)

    implementation(projects.analytics.api)

    implementation(libs.androidx.appcompat)
    implementation(libs.timber)
    implementation(libs.play.core)
}
