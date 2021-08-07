plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
}

description = "Jafama integration module"

dependencies {
    api(libs.jafama)
    api(projects.kmathCore)
}

repositories.mavenCentral()

readme {
    maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))

    feature("jafama-double", "src/main/kotlin/space/kscience/kmath/jafama/") {
        "Double ExtendedField implementations based on Jafama"
    }
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("space.kscience.kmath.misc.UnstableKMathAPI")
}
