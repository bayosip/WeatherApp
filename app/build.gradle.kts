import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.nooro.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nooro.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val apiKey = properties["api-key"] as String
        require(apiKey.isNotEmpty()) {
            "Register your api key from developer and place it in local.properties as `API_KEY`"
        }
//        buildConfigField("String", "API_KEY", apiKey)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
        kotlinCompilerVersion = "2.0.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
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

    //Dependency Injection With Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.datastore.core.android)
    kapt( libs.hilt.compiler)
    kapt (libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Network Http connection
    implementation (libs.retrofit)
    // define any required OkHttp artifacts without version
    implementation(libs.okhttp)
    implementation(libs.okhttp.v4110)
    implementation(libs.logging.interceptor)
    implementation (libs.gson)
    implementation (libs.converter.gson)
    implementation (libs.coil.compose)
    //preferences datastore
    implementation (libs.androidx.datastore.preferences)
    //json
    implementation (libs.gson)
    implementation(libs.kotlinx.serialization.json)

    //noinspection LifecycleAnnotationProcessorWithJava8
    kapt (libs.androidx.lifecycle.compiler)

    //Coil
    implementation (libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}