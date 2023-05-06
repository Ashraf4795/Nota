
object Dependencies {

    object Android {
        const val core = "androidx.core:core-ktx:${Versions.androidxCoreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val material = "com.google.android.material:material:${Versions.androidMaterial}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        const val lifeCycleExtension = "android.arch.lifecycle:extensions:${Versions.lifeCycle}"
        const val lifeCycleViewModel = "android.arch.lifecycle:viewmodel:${Versions.lifeCycle}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    }

    object Coroutine {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room_version}"
        const val compiler = "androidx.room:room-compiler:${Versions.room_version}"
        const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
        const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
    }

    object AndroidTest {
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val junit = "androidx.test.ext:junit:${Versions.androidJunit4_version}"
    }

    object Dagger {
        const val core = "com.google.dagger:dagger:${Versions.dagger_version}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger_version}"
    }

}