plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(projects.kmathCore)
        }
    }
}

readme {
    description = "Complex numbers and quaternions."
    maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))

    feature(
        id = "complex",
        description = "Complex Numbers",
        ref = "src/commonMain/kotlin/space/kscience/kmath/complex/Complex.kt"
    )

    feature(
        id = "quaternion",
        description = "Quaternions",
        ref = "src/commonMain/kotlin/space/kscience/kmath/complex/Quaternion.kt"
    )
}
