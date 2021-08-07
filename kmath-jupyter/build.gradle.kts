plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
    id(miptNpm.plugins.kotlin.jupyter.api.get().pluginId)
}

dependencies {
    api(miptNpm.kotlinx.html)
    api(projects.kmathAst)
    api(projects.kmathComplex)
    api(projects.kmathForReal)
}

readme.maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("space.kscience.kmath.misc.UnstableKMathAPI")
}

tasks.processJupyterApiResources {
    libraryProducers = listOf("space.kscience.kmath.jupyter.KMathJupyter")
}
