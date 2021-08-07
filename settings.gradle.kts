enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement.repositories {
    maven("https://repo.kotlin.link")
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

rootProject.name = "kmath"

dependencyResolutionManagement {
    repositories {
        maven("https://repo.kotlin.link")
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }

    versionCatalogs.create("miptNpm") {
        from("ru.mipt.npm:version-catalog:0.10.2")
    }
}

include(
    ":kmath-memory",
    ":kmath-complex",
    ":kmath-core",
    ":kmath-coroutines",
    ":kmath-functions",
    ":kmath-histograms",
    ":kmath-commons",
    ":kmath-viktor",
    ":kmath-stat",
    ":kmath-nd4j",
    ":kmath-dimensions",
    ":kmath-for-real",
    ":kmath-geometry",
    ":kmath-ast",
    ":kmath-ejml",
    ":kmath-kotlingrad",
    ":kmath-tensors",
    ":kmath-jupyter",
    ":kmath-symja",
    ":kmath-jafama",
    ":examples",
    ":benchmarks",
)
