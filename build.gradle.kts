// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {


    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Config.ClassPath.android_gradle_plugin)
        classpath(Config.ClassPath.kotlin_plugin)
        classpath(Config.ClassPath.safe_arg_classpath)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}