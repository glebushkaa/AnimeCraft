plugins {
    `kotlin-dsl`
}

group = "com.animecraft.build.logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)

    implementation(libs.ktlint.gradle)
    implementation(libs.detekt.gradle)

    /**
     * Workaround for bug when [dagger.hilt.android.plugin] is not found
     * in [assistant.app.android.feature.gradle.kts]
     * */
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.46.1")

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
