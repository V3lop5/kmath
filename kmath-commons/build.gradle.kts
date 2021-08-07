plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
}

description = "Commons math binding for kmath"

dependencies {
    api(libs.commons.math3)
    api(projects.kmathComplex)
    api(projects.kmathCoroutines)
    api(projects.kmathStat)
    api(projects.kmathFunctions)
}

readme.maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
