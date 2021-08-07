plugins {
    id(miptNpm.plugins.gradle.jvm.get().pluginId)
}

description = "ND4J NDStructure implementation and according NDAlgebra classes"

dependencies {
    api(projects.kmathTensors)
    api(libs.nd4j.api)
    testImplementation(libs.nd4j.native.platform)
    testImplementation(libs.slf4j.simple)
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))
    feature(id = "nd4jarraystructure") { "NDStructure wrapper for INDArray" }
    feature(id = "nd4jarrayrings") { "Rings over Nd4jArrayStructure of Int and Long" }
    feature(id = "nd4jarrayfields") { "Fields over Nd4jArrayStructure of Float and Double" }
}
