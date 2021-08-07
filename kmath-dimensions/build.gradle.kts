plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

description = "A proof of concept module for adding type-safe dimensions to structures"

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(projects.kmathCore)
        }
    }

    jvmMain {
        dependencies {
            api(kotlin("reflect"))
        }
    }
}

readme.maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
