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
    implementation(projects.core.activityHolder)
    implementation(projects.core.common)
    implementation(projects.core.navigation)
    implementation(projects.core.network.api)
    implementation(projects.core.network.impl)
    implementation(projects.core.dataStore.api)
    implementation(projects.core.dataStore.impl)
    implementation(projects.core.files)

    implementation(projects.ad.api)
    implementation(projects.ad.impl)

    implementation(projects.downloadManager)

    implementation(projects.analytics.api)
    implementation(projects.analytics.impl)

    implementation(projects.feature.splash)
    implementation(projects.feature.main)
    implementation(projects.feature.info)
    implementation(projects.feature.language)
    implementation(projects.feature.favorites)
    implementation(projects.feature.settings)
    implementation(projects.feature.report)
    implementation(projects.feature.downloadSkin)
    implementation(projects.feature.rating)

    implementation(libs.work.manager)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.timber)
    implementation(libs.play.core)
}
