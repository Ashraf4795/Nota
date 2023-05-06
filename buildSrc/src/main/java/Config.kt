import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

object Config {
    const val namespace = "com.nota"
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    const val compileSdkVersion = 33
    const val jvmTarget = "17"
    const val versionNumber = 1
    const val versionName = "1.0"
    object Modules {
        const val app = ":app"
    }

    object Plugins {
        const val android_application = "com.android.application"
        const val kotlin_android = "kotlin-android"
        const val google_gms_service = "com.google.gms.google-services"
        const val kotlin_kapt = "kotlin-kapt"
    }
}

fun <T> NamedDomainObjectContainer<T>.release(action: Action<T>): T {
    return this.getByName("release", action)
}

fun <T> NamedDomainObjectContainer<T>.debug(action: Action<T>): T {
    return this.getByName("debug", action)
}
