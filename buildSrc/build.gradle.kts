plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version "1.5.21"
}

repositories {
    maven("https://repo.kotlin.link")
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
}
