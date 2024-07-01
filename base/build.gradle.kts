plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    //alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.compose.compiler)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "uk.co.ht.base"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
}

dependencies {

    kapt(libs.hilt.compiler)

    implementation(libs.material)

    implementation (libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.accompanist.systemuicontroller)

    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.adapter.rxjava3)
    api(libs.logging.interceptor)

    implementation(libs.kotlin.stdlib)
    implementation(libs.hilt.android)

    implementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
