import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    version.set("1.2.1")
    debug.set(false)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)

    filter {
        exclude { element ->
            element.file.path.contains("generated") ||
                    element.file.path.contains("build")
        }
    }
}

kotlin {
    // Antes: jvm()
    // Agora o alvo JVM se chama "desktop"
    jvm("desktop")

    android {
        namespace = "com.app.diario.shared"

        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.ktor.client.okhttp)

        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.adaptive)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.datastore.preferences)
            implementation(libs.okio)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.compose.ui:ui-test:1.11.1")
            implementation(libs.ktor.client.mock)
            implementation(libs.turbine)
        }

        val desktopMain by getting {
            kotlin.srcDir("src/jvmMain/kotlin")
            dependencies {
                implementation(libs.kotlinx.coroutinesSwing)
                implementation(libs.ktor.client.cio)
            }
        }

        val desktopTest by getting {
            kotlin.srcDir("src/jvmTest/kotlin")
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.desktop.uiTestJUnit4)
                implementation(libs.ktor.client.mock)
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}