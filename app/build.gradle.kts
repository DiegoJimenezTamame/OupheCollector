plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Add this line
    id("androidx.navigation.safeargs.kotlin") // Add this line
}

android {
    namespace = "local.ouphecollector"
    compileSdk = 34

    defaultConfig {
        applicationId = "local.ouphecollector"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "17" // Or your desired version, must match Java
    }
}

dependencies {

    //Android Core Dependencies
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-process:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.7.0")


    //Navigation Components
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    //Retrofit for API Calls
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (for Retrofit)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //Image processing
    implementation ("com.google.mlkit:text-recognition:16.0.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")

    //Camera X
    implementation ("androidx.camera:camera-core:1.4.1")
    implementation ("androidx.camera:camera-camera2:1.4.1")
    implementation ("androidx.camera:camera-lifecycle:1.4.1")
    implementation ("androidx.camera:camera-view:1.4.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Or the latest version




}