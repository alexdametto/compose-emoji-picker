plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    `maven-publish`
}

android {
    namespace = "dev.alexdametto.compose_emoji_picker"
    compileSdk = 35

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime.android)

    // Material
    implementation(libs.material)
    implementation(libs.androidx.material3.android)

    // Tooling (previews, ...)
    implementation(libs.androidx.ui.tooling.preview.android)
    debugImplementation(libs.androidx.ui.tooling)

    // Lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Networking
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.gson)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "dev.alexdametto"
            artifactId = "compose-emoji-picker"
            version = "1.0.0-beta"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}