import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
//    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    jvmToolchain(ProjectConfig.javaVersion.toString().toInt())
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
            "-Xwhen-guards",
            "-Xnon-local-break-continue",
            "-Xexpect-actual-classes",
            "-Xnested-type-aliases",
            "-Xcontext-sensitive-resolution",
            "-Xdata-flow-based-exhaustiveness",
            "-Xallow-holdsin-contract",
            "-Xallow-contracts-on-more-functions",
            "-Xallow-condition-implies-returns-contracts"
        )

        optIn.addAll(
            "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
            "androidx.compose.material3.ExperimentalMaterial3Api",
            "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
            "androidx.compose.foundation.ExperimentalFoundationApi",
            "androidx.compose.foundation.ExperimentalFoundationApi",
            "androidx.compose.ui.ExperimentalComposeUiApi",
            "kotlinx.serialization.ExperimentalSerializationApi",
            "kotlin.time.ExperimentalTime",
            "kotlinx.cinterop.ExperimentalForeignApi",
            "org.koin.core.annotation.KoinExperimentalAPI",
        )
        extraWarnings.set(true)
    }
    @Suppress("DEPRECATION")
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.room.sqlite.wrapper)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.slf4j.simple)
        }
        // KSP Common sourceSet
        commonMain.configure {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.ui.backhandler)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.savedstate.compose)

            implementation(libs.bundles.koin)
            api(libs.koin.annotations)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.settings)
            implementation(libs.bundles.nav)

            // Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)
//            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.properties)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.connectivity.core)
            implementation(libs.connectivity.device)
            implementation(libs.connectivity.compose.device)

            if (ProjectConfig.IS_DEBUG)
                implementation(libs.ktor.monitor.logging)
            else
                implementation(libs.ktor.monitor.logging.no.op)
            implementation(libs.kotlinx.datetime)
            implementation(libs.scanner)

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

buildkonfig {
    packageName = ProjectConfig.packageName
    defaultConfigs {
        buildConfigField(Type.STRING, "packageName", ProjectConfig.packageName, const = true)
        buildConfigField(Type.STRING, "versionName", ProjectConfig.versionName, const = true)
        buildConfigField(
            Type.INT,
            "versionCode",
            ProjectConfig.versionCode.toString(),
            const = true
        )
        buildConfigField(Type.BOOLEAN, "IS_DEBUG", ProjectConfig.IS_DEBUG.toString(), const = true)
        buildConfigField(
            Type.STRING, "BASE_URL",
            if (ProjectConfig.IS_DEBUG)
                ProjectConfig.BASE_URL_DEV
            else
                ProjectConfig.BASE_URL_LIVE,
            const = true
        )
    }
}

android {
    namespace = ProjectConfig.packageName
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.packageName
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
    }
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            keyAlias = "androiddebugkey"
            storePassword = "android"
            keyPassword = "android"
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
//        debug {
//            isDebuggable = false
//            isMinifyEnabled = true
//            isShrinkResources = true
//        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    applicationVariants.all {
        outputs.filterIsInstance<com.android.build.gradle.internal.api.BaseVariantOutputImpl>()
            .forEach { output ->
                output.outputFileName = "${rootProject.name.replace(' ', '_')}_v"
                output.outputFileName += "$versionName-$name.apk"
            }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)

    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)

    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}
//configurations.all {
//    exclude(group = "io.insert-koin", module = "koin-core-annotations-jvm")
//}


// Trigger Common Metadata Generation from Native tasks
tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }
    .configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }

tasks.register("updatePlistVersion") {
    doLast {
        val plistFile = rootProject.file("iosApp/iosApp/Info.plist")
        var content = plistFile.readText()

        val updates = mapOf(
            "CFBundleVersion" to ProjectConfig.versionCode,
            "CFBundleShortVersionString" to ProjectConfig.versionName
        )

        updates.forEach { (key, value) ->
            val regex = Regex(
                """<key>$key</key>\s*<string>.*?</string>""",
                RegexOption.DOT_MATCHES_ALL
            )
            content = if (regex.containsMatchIn(content)) {
                regex.replace(content) {
                    "<key>$key</key>\n\t<string>$value</string>"
                }
            } else {
                content.replaceFirst(
                    "<dict>",
                    "<dict>\n\t<key>$key</key>\n\t<string>$value</string>"
                )
            }
        }

        plistFile.writeText(content)
    }
}

tasks.named("preBuild") {
    dependsOn("updatePlistVersion")
}