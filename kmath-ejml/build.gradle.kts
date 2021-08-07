import space.kscience.kmath.ejml.codegen.ejmlCodegen

plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
}

dependencies {
    api(libs.ejml.ddense)
    api(libs.ejml.fdense)
    api(libs.ejml.dsparse)
    api(libs.ejml.fsparse)
    api(projects.kmathCore)
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))

    feature(
        id = "ejml-vector",
        ref = "src/main/kotlin/space/kscience/kmath/ejml/EjmlVector.kt"
    ) { "Point implementations." }

    feature(
        id = "ejml-matrix",
        ref = "src/main/kotlin/space/kscience/kmath/ejml/EjmlMatrix.kt"
    ) { "Matrix implementation." }

    feature(
        id = "ejml-linear-space",
        ref = "src/main/kotlin/space/kscience/kmath/ejml/EjmlLinearSpace.kt"
    ) { "LinearSpace implementations." }
}

kotlin.sourceSets.main {
    val codegen by tasks.creating {
        ejmlCodegen(kotlin.srcDirs.first().absolutePath + "/space/kscience/kmath/ejml/_generated.kt")
    }

    kotlin.srcDirs(files().builtBy(codegen))
}
