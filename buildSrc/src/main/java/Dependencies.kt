@file:Suppress("MayBeConstant", "ConstantConditionIf", "unused")

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven

val repos: RepositoryHandler.() -> Unit get() = {
    google()
    jcenter()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx"  )
}

val ScriptHandlerScope.classpathDependencies: DependencyHandlerScope.() -> Unit get() = {
    classpath( kotlin("gradle-plugin", Versions.kotlin) )
    classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}" )
    classpath("com.android.tools.build:gradle:3.3.0" )
    classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}" )
}

object Project {
    val id =                "studio.forface.freshtv"
    val targetSdk =         28
    val minSdk =            21

    private val major =     2
    private val minor =     0
    private val channel =   0 // 0: build, 1: alpha, 2: beta, 3: RC, 4: stable
    private val patch =     0
    private val build =     1

    val versionName: String get() {
        val baseName = "$major.$minor"
        val suffixName = suffixVersionName()

        return "$baseName$suffixName"
    }

    private fun suffixVersionName(): String {
        val ch = channel
        if ( build > 0 || ch == 0 ) return versionBuildName()

        var vChannel = ""
        when (ch) {
            1 -> vChannel = "-alpha"
            2 -> vChannel = "-beta"
            3 -> vChannel = "-RC"
            //else if ( ch == 4 ) vChannel = "" // stable
        }

        val pt = patch
        if ( pt > 0 ) vChannel = "$vChannel-$pt"

        return vChannel
    }

    private fun versionBuildName(): String {
        var vBuild = ""
        val bv = build

        if ( bv > 0 ) {
            vBuild = "-build$channel$patch$bv"

            val ch = channel
            if ( ch == 3 )
                throw IllegalArgumentException( "build is $bv, but channel is $ch ( stable )" )
        }

        return vBuild
    }

    val versionCode: Int get() {
        // pattern:
        // major minor channel patch build
        // 00    00    0      00     00

        val build   = build   *            1
        val patch   = patch   *         1_00
        val channel = channel *      1_00_00
        val minor   = minor   *    1_0_00_00
        val major   = major   * 1_00_0_00_00

        return major + minor + patch + channel + build
    }
}

object Versions {
    val kotlin =                        "1.3.20"
    val coroutines =                    "1.1.1"
    val koin =                          "1.0.2"
    val mockk =                         "1.9"
    val serialization =                 "0.10.0"
    val sqldelight =                    "1.0.0"

    val android_arch =                  "2.0.0-beta01"
    val android_constraint_layout =     "2.0.0-alpha1"
    val android_espresso =              "3.1.1"
    val android_gradlePlugin =          "3.3.0-rc02"
    val android_ktx =                   "1.1.0-alpha03"
    val android_lifecycle =             "2.0.0-beta01"
    val android_material =              "1.0.0"
    val android_navigation =            "1.0.0-alpha06"
    val android_support =               "1.0.0-beta01"
    val android_test_runner =           "1.1.1"
    val android_work =                  "1.0.0-alpha12"
}

object Libs {

    /* Kotlin */
    val kotlin =                        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val test =                          "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val test_junit =                    "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    val coroutines =                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutines_android =            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val serialization =                 "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}"

    val mockk =                         "io.mockk:mockk:${Versions.mockk}"
    val sqldelight_android_driver =     "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    val sqldelight_android_paging =     "com.squareup.sqldelight:android-paging-extensions:${Versions.sqldelight}"

    /* Android */
    object Android {
        val appcompat =                 "androidx.appcompat:appcompat:${Versions.android_support}"
        val constraint_layout =         "androidx.constraintlayout:constraintlayout:${Versions.android_constraint_layout}"
        val design =                    "com.android.support:design:${Versions.android_support}"
        val espresso =                  "androidx.test.espresso:espresso-core:${Versions.android_espresso}"
        val ktx =                       "androidx.core:core-ktx:${Versions.android_ktx}"
        val lifecycle_runtime =         "androidx.lifecycle:lifecycle-runtime:${Versions.android_lifecycle}"
        val lifecycle_compiler =        "androidx.lifecycle:lifecycle-compiler:${Versions.android_lifecycle}"
        val lifecycle_extensions =      "androidx.lifecycle:lifecycle-extensions:${Versions.android_lifecycle}"
        val lifecycle_jdk8 =            "androidx.lifecycle:lifecycle-common-java8:${Versions.android_lifecycle}"
        val material =                  "com.google.android.material:material:${Versions.android_material}"
        val support_annotations =       "com.android.support:support-annotations:28.0.0"
        val test_runner =               "com.android.support.test:runner:${Versions.android_test_runner}"
    }
}