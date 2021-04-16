/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

import ru.mipt.npm.gradle.Maturity

plugins {
    kotlin("multiplatform")
    id("ru.mipt.npm.gradle.common")
}

kotlin.sourceSets.commonMain {
    dependencies {
        api(project(":kmath-core"))
    }
}

readme {
    description = """
        Extension module that should be used to achieve numpy-like behavior. 
        All operations are specialized to work with `Double` numbers without declaring algebraic contexts.
        One can still use generic algebras though.
        """.trimIndent()
    maturity = Maturity.EXPERIMENTAL
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
