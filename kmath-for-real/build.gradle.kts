plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

kotlin.sourceSets.commonMain {
    dependencies {
        api(projects.kmathCore)
    }
}

readme {
    description = """
        Extension module that should be used to achieve numpy-like behavior.
        All operations are specialized to work with `Double` numbers without declaring algebraic contexts.
        One can still use generic algebras though.
        """.trimIndent()
    maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))

    feature(
        id = "DoubleVector",
        description = "Numpy-like operations for Buffers/Points",
        ref = "src/commonMain/kotlin/space/kscience/kmath/real/DoubleVector.kt"
    )

    feature(
        id = "DoubleMatrix",
        description = "Numpy-like operations for 2d real structures",
        ref = "src/commonMain/kotlin/space/kscience/kmath/real/DoubleMatrix.kt"
    )

    feature(
        id = "grids",
        description = "Uniform grid generators",
        ref = "src/commonMain/kotlin/space/kscience/kmath/structures/grids.kt"
    )
}
