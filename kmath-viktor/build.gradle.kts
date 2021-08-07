plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
}

description = "Binding for https://github.com/JetBrains-Research/viktor"

dependencies {
    api(projects.kmathCore)
    api("org.jetbrains.bio:viktor:1.1.0")
}

readme.maturity = ru.mipt.npm.gradle.Maturity.DEVELOPMENT

