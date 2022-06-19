plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.kk.eazypariksha.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
        implementation(project(":shared"))
        implementation("androidx.core:core-ktx:1.8.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
        implementation("com.google.android.material:material:1.6.1")

        val composeVersion = "1.2.0-rc01"
        implementation("androidx.activity:activity-compose:1.4.0")
        implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
        implementation("androidx.compose.material:material:1.1.1")
        implementation("androidx.compose.material:material-icons-extended:$composeVersion")
        implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
        implementation("androidx.compose.ui:ui-util:$composeVersion")
        implementation("androidx.navigation:navigation-compose:2.4.2")
        implementation("androidx.compose.runtime:runtime:$composeVersion")
        debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

        val accompanistVersion = "0.24.11-rc"
        implementation("com.google.accompanist:accompanist-insets-ui:$accompanistVersion")

        implementation("com.afollestad.material-dialogs:datetime:3.3.0")
    }
}
