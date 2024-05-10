plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.liveperson.compose.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.liveperson.compose.sample"
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
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(mapOf("path" to ":common")))
    implementation(project(mapOf("path" to ":common-ui")))
    implementation(project(mapOf("path" to ":external-auth")))
    implementation(fileTree(mapOf("dir" to "../aars", "include" to listOf("*.aar"))))

    implementation(libs.material)
    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.play.services.maps)
    implementation(libs.bundles.liveperson)

    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.network)

    testImplementation(libs.junit)
    testImplementation(libs.bundles.koin.test)

    androidTestImplementation(libs.bundles.androidx.test)
    androidTestImplementation(platform(libs.compose.test.bom))
    androidTestImplementation(libs.bundles.compose.test)

    debugImplementation(libs.bundles.compose.debug)
}