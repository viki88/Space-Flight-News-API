plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.serialization.plugin)
//    alias(libs.plugins.kotlin.jvm)
}

android {
    namespace = "com.vikination.spaceflightnewsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vikination.spaceflightnewsapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["auth0Domain"] = "@string/com_auth0_domain"
        manifestPlaceholders["auth0Scheme"] = "app"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.mock)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.auth0)
    implementation(libs.hilt.android)
    implementation(libs.compose.navigation)
    implementation(libs.androidx.storage)
    implementation(libs.hilt.navigation)
    implementation(libs.androidx.work)
    implementation(libs.hilt.common)
    implementation(libs.logging.interceptor)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.material.icon)
    implementation(libs.datastore)
    implementation(libs.kotlin.serialization)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt{
    correctErrorTypes = true
}