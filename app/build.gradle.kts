plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
//    kotlin("android") version "1.9.0"
//    kotlin("plugin.serialization") version "1.9.0"
    id("kotlin-kapt")
//    alias(libs.plugins.hilt)
}

android {
    namespace = "com.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Kotlin
    implementation (libs.kotlin.stdlib)

    // Core dependencies
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.androidx.material)
    implementation(libs.androidx.material3)

    // Jetpack Compose
    implementation (libs.ui)
    implementation (libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
//    implementation (libs.androidx.lifecycle.runtime.ktx.v271)
//    implementation (libs.androidx.activity.compose.v172)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//    implementation (libs.hilt.android)
//    kapt(libs.hilt.android.compiler)
//    kapt(libs.hilt.compiler)

    // Retrofit & Moshi
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    // Coil for image loading
    implementation (libs.coil.compose)

    // Unit Testing
    testImplementation (libs.junit)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test)

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}