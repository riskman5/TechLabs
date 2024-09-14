pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.22"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "lab-1"
include("application")
include("infrastructure")
include("presentation")
include("application")
include("infrastructure")
include("presentation")
