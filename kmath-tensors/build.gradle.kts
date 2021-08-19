plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

kotlin.sourceSets {
    all {
        languageSettings.useExperimentalAnnotation("space.kscience.kmath.misc.UnstableKMathAPI")
    }

    commonMain {
        dependencies {
            api(projects.kmathStat)
        }
    }
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))

    feature(
        id = "tensor algebra",
        ref = "src/commonMain/kotlin/space/kscience/kmath/tensors/api/TensorAlgebra.kt"
    ) { "Basic linear algebra operations on tensors (plus, dot, etc.)" }

    feature(
        id = "tensor algebra with broadcasting",
        ref = "src/commonMain/kotlin/space/kscience/kmath/tensors/core/BroadcastDoubleTensorAlgebra.kt"
    ) { "Basic linear algebra operations implemented with broadcasting." }

    feature(
        id = "linear algebra operations",
        ref = "src/commonMain/kotlin/space/kscience/kmath/tensors/api/LinearOpsTensorAlgebra.kt"
    ) { "Advanced linear algebra operations like LU decomposition, SVD, etc." }
}
