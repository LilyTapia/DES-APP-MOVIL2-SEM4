plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "cl.duoc.veterinaria.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        // Habilitar desugaring para usar java.time en API < 26
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Dependencia necesaria para que funcione el desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    
    implementation(libs.androidx.core.ktx)
}
