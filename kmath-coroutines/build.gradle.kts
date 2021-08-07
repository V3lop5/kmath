plugins {
    id(miptNpm.plugins.gradle.mpp.get().pluginId)
    id(miptNpm.plugins.gradle.native.get().pluginId)
}

kotlin.sourceSets {
    all {
        with(languageSettings) {
            useExperimentalAnnotation("kotlinx.coroutines.InternalCoroutinesApi")
            useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
        }
    }

    commonMain {
        dependencies {
            api(miptNpm.kotlinx.coroutines.core)
            api(projects.kmathComplex)
        }
    }
}

readme.maturity = ru.mipt.npm.gradle.Maturity.EXPERIMENTAL
