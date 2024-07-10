plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "uk.co.ht.cryptobuzz"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.co.ht.cryptobuzz"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        compose = true
        buildConfig = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    packaging {
        resources.excludes += "META-INF/LICENSE*"
        resources.excludes += "META-INF/NOTICE"
        resources.excludes += "META-INF/NOTICE.txt"
    }

    kapt {
        arguments {
            arg("dagger.fastInit", "enabled")
            arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
            arg("dagger.hilt.android.internal.projectType", "APP")
            arg("dagger.hilt.internal.useAggregatingRootProcessor", "true")
            arg("kapt.kotlin.generated", layout.buildDirectory.dir("generated/source/kaptKotlin").get().asFile.path)
        }
    }
}

dependencies {

    debugImplementation(libs.androidx.ui.tooling)
    //testImplementation(libs.testng)
    kapt(libs.hilt.compiler)


    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.animation)

    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.kotlin.bom))

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.coil.kt.compose)

    implementation(libs.kotlin.stdlib)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":base"))

    //Test dependencies

    testImplementation(libs.jetbrains.kotlinx.coroutines.test)

    testImplementation(libs.mockk.mockk)
    testImplementation(libs.mockk.mockk.android)
    testImplementation(libs.mockk.agent)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test.v164)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(kotlin("test"))

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(libs.ui.tooling)
}

tasks.withType<Test> {
    useJUnitPlatform()
}