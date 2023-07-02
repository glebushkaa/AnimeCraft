pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AnimeCraft"

include(":app")

include(":core:domain")
include(":core:log")
include(":core:common-android")
include(":core:data")
include(":core:model")
include(":feature")
include(":core:database")
