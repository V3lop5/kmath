plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

kscience {
    useAtomic()
}

kotlin.sourceSets {
    commonMain {
        dependencies {
            api(projects.kmathCore)
        }
    }
    commonTest {
        dependencies {
            implementation(projects.kmathForReal)
        }
    }
}

readme {
    maturity = ru.mipt.npm.gradle.Maturity.PROTOTYPE
}
