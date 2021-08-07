plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.DEVELOPMENT
    description = """
        An API and basic implementation for arranging objects in a continuous memory block.
    """.trimIndent()
}
