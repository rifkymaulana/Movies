plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.movies.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.movies.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val koinComposeVersion = "3.4.1"
    val coilVersion = "2.2.2"
    val accompanistVersion = "0.28.0"
    val navVersion = "2.5.3"
    val composeVersion = "1.4.2"

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.activity:activity-compose:1.7.2")

    implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    implementation("androidx.navigation:navigation-compose:2.7.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    // FOR ROOM
    implementation("androidx.room:room-common:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")


    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.5.2")
    // END FOR ROOM

    // shared preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}