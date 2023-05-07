plugins {
    id(Config.Plugins.android_application)
    id(Config.Plugins.kotlin_android)
    id(Config.Plugins.kotlin_kapt)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = Config.namespace

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        compileSdk = Config.compileSdkVersion
        versionCode = Config.versionNumber
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            applicationIdSuffix = ".release"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
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
    implementation(Dependencies.Android.core)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.lifecycleRuntime)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.lifeCycleExtension)
    implementation(Dependencies.Android.lifeCycleViewModel)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.fragmentKtx)
    implementation(Dependencies.Android.lifecycle_viewmodel_ktx)
    //coroutine
    implementation(Dependencies.Coroutine.android)

    //navigation
    implementation(Dependencies.NavigationComponent.navigationFragment)
    implementation(Dependencies.NavigationComponent.navigationUiKtx)
    //room
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.room_ktx)
    kapt(Dependencies.Room.compiler)

    //retrofit
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.converter_gson)

    //dagger
    implementation(Dependencies.Dagger.core)
    kapt(Dependencies.Dagger.compiler)

    // lottie
    implementation(Dependencies.Lottie.lottie)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
}