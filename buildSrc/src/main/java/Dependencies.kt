@file:Suppress("MayBeConstant", "unused")

import org.gradle.api.artifacts.dsl.DependencyHandler
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

fun DependencyHandler.applyTests() = Libs.run {
    listOf( test, test_junit, mockk )
            .forEach { add("testImplementation", it ) }
}

fun DependencyHandler.applyAndroidTests() = Libs.Android.run {
    listOf( espresso, test_runner )
            .forEach { add( "androidTestImplementation", it ) }
}

object Versions {
    val kotlin =                        "1.3.20"
    val coroutines =                    "1.1.1"
    val serialization =                 "0.10.0"

    val koin =                          "2.0.0-beta-1"
    val ktor =                          "1.1.2"
    val mockk =                         "1.9"
    val sqldelight =                    "1.0.3"
    val threeten_android_bp =           "1.1.1"
    val threeten_bp =                   "1.3.2"

    val android_arch =                  "2.0.0-beta01"
    val android_constraint_layout =     "2.0.0-alpha1"
    val android_espresso =              "3.1.1"
    val android_gradlePlugin =          "3.3.0-rc02"
    val android_ktx =                   "1.1.0-alpha03"
    val android_lifecycle =             "2.0.0"
    val android_material =              "1.0.0"
    val android_navigation =            "1.0.0-alpha06"
    val android_support =               "1.0.0-beta01"
    val android_test_runner =           "1.1.1"
    val android_work =                  "1.0.0-alpha12"
}

object Libs {

    /* Kotlin */
    val kotlin =                        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines =                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutines_android =            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val serialization =                 "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}"
    val reflect =                       "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val test =                          "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val test_junit =                    "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

    val koin =                          "org.koin:koin-core:${Versions.koin}"
    val koin_android =                  "org.koin:koin-android:${Versions.koin}"
    val koin_android_viewmodel =        "org.koin:koin-android-viewmodel:${Versions.koin}"
    val ktor =                          "io.ktor:ktor-client-core:${Versions.ktor}"
    val ktor_apache =                   "io.ktor:ktor-client-apache:${Versions.ktor}"
    val mockk =                         "io.mockk:mockk:${Versions.mockk}"
    val sqldelight =                    "com.squareup.sqldelight:runtime-jvm:${Versions.sqldelight}"
    val sqldelight_android_driver =     "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    val sqldelight_android_paging =     "com.squareup.sqldelight:android-paging-extensions:${Versions.sqldelight}"
    val threeten_android_bp =           "com.jakewharton.threetenabp:threetenabp:${Versions.threeten_android_bp}"
    val threeten_bp =                   "org.threeten:threetenbp:${Versions.threeten_bp}"

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