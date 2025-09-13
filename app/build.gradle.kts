plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"     }

android {
    namespace = "com.example.finalyearproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalyearproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:19.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    implementation ("com.loopj.android:android-async-http:1.4.11")

    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    implementation ("com.github.bumptech.glide:glide:4.15.1")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    implementation ("com.android.volley:volley:1.2.1")

    implementation ("com.google.zxing:core:3.4.1")

    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")

    implementation ("com.google.android.gms:play-services-location:21.3.0")

    implementation("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")

    
        implementation(platform("androidx.compose:compose-bom:2024.05.00"))
        implementation("androidx.compose.ui:ui")
        // ...other Compose dependencies

    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.android.material:material:1.10.0")

    implementation ("com.razorpay:checkout:1.6.33")
    implementation ("com.cashfree.pg:api:2.2.8")




}