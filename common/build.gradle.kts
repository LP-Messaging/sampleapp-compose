plugins {
    id("com.android.library") version "8.1.0"
    id("org.jetbrains.kotlin.android") version "1.8.10"
    kotlin("plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.liveperson.common"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "../aars", "include" to listOf("*.aar"))))
    implementation(libs.bundles.koin)
    implementation(libs.androidx.local.broadcast.manager)

    implementation(libs.kotlinx.serialization)

    androidTestImplementation(libs.bundles.androidx.test)

    testImplementation(libs.junit)
    testImplementation(libs.bundles.koin.test)
}