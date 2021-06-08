import space.kscience.kmath.ejml.codegen.ejmlCodegen

plugins {
    kotlin("jvm")
    id("ru.mipt.npm.gradle.common")
}

dependencies {
    api("org.ejml:ejml-ddense:0.40")
    api("org.ejml:ejml-fdense:0.40")
    api("org.ejml:ejml-dsparse:0.40")
    api("org.ejml:ejml-fsparse:0.40")
    api(project(":kmath-core"))
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