plugins {
    alias(libs.plugins.androidApplication)
    //Google Console firebase
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.gotcha"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gotcha"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Picasso Library
    implementation(libs.picasso.library)


    //Google Console firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // Authentication
    implementation (libs.firebase.authentication.ui)
    implementation(libs.gms.play.services.auth)
    //DataBase
    implementation(libs.firebase.database)
}