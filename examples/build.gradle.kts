plugins {
    id(miptNpm.plugins.kotlin.jvm.get().pluginId)
}

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
    maven("https://clojars.org/repo")
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")

    maven("http://logicrunch.research.it.uu.se/maven") {
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation(projects.kmathMemory)
    implementation(projects.kmathFunctions)
    implementation(projects.kmathHistograms)
    implementation(projects.kmathCommons)
    implementation(projects.kmathViktor)
    implementation(projects.kmathNd4j)
    implementation(projects.kmathDimensions)
    implementation(projects.kmathForReal)
    implementation(projects.kmathGeometry)
    implementation(projects.kmathAst)
    implementation(projects.kmathEjml)
    implementation(projects.kmathKotlingrad)
    implementation(projects.kmathSymja)
    implementation(projects.kmathJafama)

    implementation(libs.nd4j.native)

//     uncomment if your system supports AVX2
//     val os = System.getProperty("os.name")
//
//     if (System.getProperty("os.arch") in arrayOf("x86_64", "amd64")) when {
//         os.startsWith("Windows") -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:windows-x86_64-avx2")
//         os == "Linux" -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:linux-x86_64-avx2")
//         os == "Mac OS X" -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:macosx-x86_64-avx2")
//     } else
    implementation(libs.nd4j.native.platform)

    implementation(libs.slf4j.simple)
    // plotting
    implementation(libs.plotlykt.server)
}

kotlin.sourceSets.all {
    with(languageSettings) {
        useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
        useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        useExperimentalAnnotation("space.kscience.kmath.misc.UnstableKMathAPI")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions{
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all" + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
}
