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

include(":ad:api")
include(":ad:impl")

include(":analytics:impl")
include(":analytics:api")

include(":download-manager")

include(":core:domain")
include(":core:log")
include(":core:common-android")
include(":core:data")
include(":core:model")
include(":core:database")
include(":core:activity-holder")
include(":core:files")
include(":core:network:impl")
include(":core:network:api")
include(":core:common")
include(":core:theme")
include(":core:navigation")
include(":core:data-store:api")
include(":core:data-store:impl")
include(":core:components")

include(":feature:main")
include(":feature:favorites")
include(":feature:info")
include(":feature:settings")
include(":feature:language")
include(":feature:report")
include(":feature:splash")
include(":feature:rating")
include(":feature:download-skin")
include(":core:utils")
