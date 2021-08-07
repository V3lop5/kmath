@file:Suppress("UNUSED_VARIABLE")

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField

plugins {
    alias(miptNpm.plugins.kotlin.plugin.allopen)
    alias(miptNpm.plugins.kotlinx.benchmark)
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
}

allOpen.annotation("org.openjdk.jmh.annotations.State")
sourceSets.register("benchmarks")

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
    maven("https://clojars.org/repo")
    maven("https://jitpack.io")

    maven("http://logicrunch.research.it.uu.se/maven") {
        isAllowInsecureProtocol = true
    }
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.kmathAst)
                implementation(projects.kmathCore)
                implementation(projects.kmathCoroutines)
                implementation(projects.kmathComplex)
                implementation(projects.kmathStat)
                implementation(projects.kmathDimensions)
                implementation(projects.kmathForReal)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(miptNpm.kotlinx.benchmark.runtime)
                implementation(projects.kmathCommons)
                implementation(projects.kmathJafama)
                implementation(projects.kmathEjml)
                implementation(projects.kmathNd4j)
                implementation(projects.kmathKotlingrad)
                implementation(projects.kmathViktor)
                implementation(libs.nd4j.native)
//                uncomment if your system supports AVX2
//                val os = System.getProperty("os.name")
//
//                if (System.getProperty("os.arch") in arrayOf("x86_64", "amd64")) when {
//                    os.startsWith("Windows") -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:windows-x86_64-avx2")
//                    os == "Linux" -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:linux-x86_64-avx2")
//                    os == "Mac OS X" -> implementation("org.nd4j:nd4j-native:${libs.versions.nd4j.get()}:macosx-x86_64-avx2")
//                } else
                implementation(libs.nd4j.native.platform)
            }
        }
    }
}

// Configure benchmark
benchmark {
    // Setup configurations
    targets {
        register("jvm")
    }

    fun kotlinx.benchmark.gradle.BenchmarkConfiguration.commonConfiguration() {
        warmups = 1
        iterations = 5
        iterationTime = 1000
        iterationTimeUnit = "ms"
    }

    configurations.register("buffer") {
        commonConfiguration()
        include("BufferBenchmark")
    }

    configurations.register("dot") {
        commonConfiguration()
        include("DotBenchmark")
    }

    configurations.register("expressions") {
        commonConfiguration()
        include("ExpressionsInterpretersBenchmark")
    }

    configurations.register("matrixInverse") {
        commonConfiguration()
        include("MatrixInverseBenchmark")
    }

    configurations.register("bigInt") {
        commonConfiguration()
        include("BigIntBenchmark")
    }

    configurations.register("jafamaDouble") {
        commonConfiguration()
        include("JafamaBenchmark")
    }
}

// Fix kotlinx-benchmarks bug
afterEvaluate {
    val jvmBenchmarkJar by tasks.getting(org.gradle.jvm.tasks.Jar::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}


kotlin.sourceSets.all {
    with(languageSettings) {
        useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
        useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        useExperimentalAnnotation("space.kscience.kmath.misc.UnstableKMathAPI")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all"
    }
}


readme.maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL

private val isoDateTime: DateTimeFormatter = DateTimeFormatterBuilder().run {
    parseCaseInsensitive()
    appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
    appendLiteral('-')
    appendValue(ChronoField.MONTH_OF_YEAR, 2)
    appendLiteral('-')
    appendValue(ChronoField.DAY_OF_MONTH, 2)
    appendLiteral('T')
    appendValue(ChronoField.HOUR_OF_DAY, 2)
    appendLiteral('.')
    appendValue(ChronoField.MINUTE_OF_HOUR, 2)
    optionalStart()
    appendLiteral('.')
    appendValue(ChronoField.SECOND_OF_MINUTE, 2)
    optionalStart()
    appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
    optionalStart()
    appendOffsetId()
    optionalStart()
    appendLiteral('[')
    parseCaseSensitive()
    appendZoneRegionId()
    appendLiteral(']')
    toFormatter()
}

fun noun(number: Number, singular: String, plural: String) = if (number.toLong() == 1L) singular else plural

val benchmarksProject = project

rootProject.subprojects {
    readme {
        benchmarksProject.benchmark.configurations.forEach { cfg ->
            val capitalized = StringBuilder(cfg.name)
            capitalized[0] = capitalized[0].toUpperCase()
            property("benchmark${capitalized}") {
                val launches = benchmarksProject.buildDir.resolve("reports/benchmarks/${cfg.name}")

                val resDirectory = launches.listFiles()?.maxByOrNull {
                    LocalDateTime.parse(it.name, isoDateTime).atZone(ZoneId.systemDefault()).toInstant()
                }

                if (resDirectory == null) {
                    "> **Can't find appropriate benchmark data. Try generating readme files after running benchmarks**."
                } else {
                    val reports =
                        Json.decodeFromString<List<space.kscience.kmath.benchmarks.JmhReport>>(
                            resDirectory.resolve("jvm.json").readText()
                        )

                    buildString {
                        appendLine("<details>")
                        appendLine("<summary>")
                        appendLine("Report for benchmark configuration <code>${cfg.name}</code>")
                        appendLine("</summary>")
                        appendLine()
                        val first = reports.first()

                        appendLine("* Run on ${first.vmName} (build ${first.vmVersion}) with Java process:")
                        appendLine()
                        appendLine("```")

                        appendLine(
                            "${first.jvm} ${
                                first.jvmArgs.joinToString(" ")
                            }"
                        )

                        appendLine("```")

                        appendLine(
                            "* JMH ${first.jmhVersion} was used in `${first.mode}` mode with ${first.warmupIterations} warmup ${
                                noun(first.warmupIterations, "iteration", "iterations")
                            } by ${first.warmupTime} and ${first.measurementIterations} measurement ${
                                noun(first.measurementIterations, "iteration", "iterations")
                            } by ${first.measurementTime}."
                        )

                        appendLine()
                        appendLine("| Benchmark | Score |")
                        appendLine("|:---------:|:-----:|")

                        reports.forEach { report ->
                            appendLine("|`${report.benchmark}`|${report.primaryMetric.score} &plusmn; ${report.primaryMetric.scoreError} ${report.primaryMetric.scoreUnit}|")
                        }

                        appendLine("</details>")
                    }
                }
            }
        }
    }
}
