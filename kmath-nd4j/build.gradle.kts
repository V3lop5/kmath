plugins {
    kotlin("jvm")
    id("ru.mipt.npm.gradle.common")
}

description = "ND4J NDStructure implementation and according NDAlgebra classes"

dependencies {
    api(project(":kmath-tensors"))
    api("org.nd4j:nd4j-api:1.0.0-beta7")
    testImplementation("org.nd4j:nd4j-native:1.0.0-beta7")
    testImplementation("org.nd4j:nd4j-native-platform:1.0.0-beta7")
    testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
    propertyByTemplate("artifact", rootProject.file("docs/templates/ARTIFACT-TEMPLATE.md"))
    feature(id = "nd4jarraystructure") { "NDStructure wrapper for INDArray" }
    feature(id = "nd4jarrayrings") { "Rings over Nd4jArrayStructure of Int and Long" }
    feature(id = "nd4jarrayfields") { "Fields over Nd4jArrayStructure of Float and Double" }
}