plugins {
    id("com.android.library") version "8.1.0"
    id("org.jetbrains.kotlin.android") version "1.8.10"
}

android {
    namespace = "com.liveperson.compose.common_ui"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {

    implementation(project(mapOf("path" to ":common")))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.androidx)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.network)
    implementation(libs.play.services.maps)
    implementation(libs.bundles.liveperson)

    testImplementation(libs.junit)
    testImplementation(libs.bundles.koin.test)

    androidTestImplementation(libs.bundles.androidx.test)
    androidTestImplementation(platform(libs.compose.test.bom))
    androidTestImplementation(libs.bundles.compose.test)

    debugImplementation(libs.bundles.compose.debug)
}