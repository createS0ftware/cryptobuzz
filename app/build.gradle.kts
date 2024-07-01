plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.compose.compiler)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

//id("com.google.devtools.ksp") version "1.7.20-1.0.8"
}

android {
    namespace = "uk.co.ht.cyberbuzz"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.co.ht.cyberbuzz"
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
}

dependencies {

    kapt(libs.hilt.compiler)


    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)

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
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.coil.kt.compose)

    implementation(libs.kotlin.stdlib)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":base"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}