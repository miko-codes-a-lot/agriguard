import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.realm.kotlin)
    id("kotlin-kapt")
    alias(libs.plugins.android.dagger.hilt)
}

android {
    namespace = "com.example.agriguard"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.agriguard"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val envFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(envFile.inputStream())

        buildConfigField("String", "REALM_APP_ID", properties.getProperty("realm.app.id"))
        buildConfigField("String", "REALM_API_KEY", properties.getProperty("realm.app.api.key"))

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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.mpandroidchart)
    implementation(libs.coil.compose)
    implementation(libs.jbcrypt)
    implementation(libs.realm.kotlin.base)
    implementation(libs.realm.kotlin.sync)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.android.hilt)
    kapt(libs.android.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation (libs.accompanist.permissions)
    implementation(libs.kotlinx.datetime)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.itextpdf)
}

kapt {
    correctErrorTypes = true
}
