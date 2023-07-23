import com.animecraft.build.logic.convention.AnimeCraftConfig.APPLICATION_ID

plugins {
    id("animecraft.android.library")
    id("animecraft.lint")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "$APPLICATION_ID.core.ad.impl"
}

dependencies {
    implementation(projects.ad.api)

    implementation(projects.core.activityHolder)
    implementation(projects.core.log)

    implementation(libs.play.services.ads)
    implementation(libs.work.manager)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
