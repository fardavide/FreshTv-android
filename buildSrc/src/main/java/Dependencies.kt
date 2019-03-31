@file:Suppress("MayBeConstant")

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
    maven("https://kotlin.bintray.com/kotlinx" )
    maven("https://maven.fabric.io/public" )
    // mavenLocal()
}

val ScriptHandlerScope.classpathDependencies: DependencyHandlerScope.() -> Unit get() = {
    classpath( kotlin("gradle-plugin", Versions.kotlin) )
    classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}" )
    classpath("com.android.tools.build:gradle:${Versions.android_gradle_plugin}" )
    classpath("io.fabric.tools:gradle:${Versions.fabric}")
    classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}" )
    classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.android_navigation}" )
}

fun DependencyHandler.applyTests() = Libs.run {
    listOf( test, test_junit, mockk )
            .forEach { add("testImplementation", it ) }
}

fun DependencyHandler.applyAndroidTests() {
    Libs.Android.run {
        listOf( robolectric )
            .forEach { add( "testImplementation", it ) }
    }
    Libs.run {
        listOf( test, test_junit, mockk_android )
            .forEach { add("androidTestImplementation", it ) }
    }
    Libs.Android.run {
        listOf( espresso, test_core, test_fragment, test_rules, test_runner )
            .forEach { add( "androidTestImplementation", it ) }
    }
}

object Versions {
    val kotlin =                        "1.3.21"
    val coroutines =                    "1.1.1"
    val serialization =                 "0.10.0"

    val fabric =                        "1.25.4"
    val firebase_crashlytics_android =  "2.9.8"
    val koin =                          "2.0.0-beta-1"
    val ktor =                          "1.1.3"
    val mockk =                         "1.9"
    val sqldelight =                    "1.1.1"
    val threeten_android_bp =           "1.1.1"
    val threeten_bp =                   "1.3.2"
    val timber =                        "4.7.1"

    val android_arch =                  "2.0.0-beta01"
    val android_constraint_layout =     "2.0.0-alpha1"
    val android_cue =                   "1.1"
    val android_espresso =              "3.1.1"
    val android_exo_player =            "2.9.6"
    val android_gradle_plugin =         "3.3.0"
    val android_ktx =                   "1.1.0-alpha03"
    val android_lifecycle =             "2.1.0-alpha02"
    val android_material =              "1.0.0"
    val android_material_bottom_bar =   "1.1-beta-11"
    val android_navigation =            "1.0.0"
    val android_paging =                "2.1.0"
    val android_robolectric =           "4.2"
    val android_room =                  "2.1.0-alpha04"
    val android_support =               "1.0.0-beta01"
    val android_test_core =             "1.0.0"
    val android_test_fragment =         "1.1.0-alpha03"
    val android_test_runner =           "1.1.0"
    val android_theia =                 "0.3-alpha-1"
    val android_view_pager2 =           "1.0.0-alpha02"
    val android_view_state_store =      "1.2-alpha-3"
    val android_work =                  "1.0.0"
}

@Suppress("unused")
object Libs {

    /* Kotlin */
    val kotlin =                                "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines =                            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutines_android =                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val serialization =                         "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}"
    val reflect =                               "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val test =                                  "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val test_junit =                            "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

    val firebase_crashlytics_android =          "com.crashlytics.sdk.android:crashlytics:${Versions.firebase_crashlytics_android}"
    val koin =                                  "org.koin:koin-core:${Versions.koin}"
    val koin_android =                          "org.koin:koin-android:${Versions.koin}"
    val koin_android_viewmodel =                "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val ktor =                                  "io.ktor:ktor-client-core:${Versions.ktor}"
    val ktor_apache =                           "io.ktor:ktor-client-apache:${Versions.ktor}"
    val mockk =                                 "io.mockk:mockk:${Versions.mockk}"
    val mockk_android =                         "io.mockk:mockk-android:${Versions.mockk}"
    val sqldelight =                            "com.squareup.sqldelight:runtime-jvm:${Versions.sqldelight}"
    val sqldelight_android_driver =             "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    val sqldelight_android_paging =             "com.squareup.sqldelight:android-paging-extensions:${Versions.sqldelight}"
    val threeten_android_bp =                   "com.jakewharton.threetenabp:threetenabp:${Versions.threeten_android_bp}"
    val threeten_bp =                           "org.threeten:threetenbp:${Versions.threeten_bp}"
    val timber_android =                        "com.jakewharton.timber:timber:${Versions.timber}"

    /* Android */
    object Android {
        val appcompat =                         "androidx.appcompat:appcompat:${Versions.android_support}"
        val constraint_layout =                 "androidx.constraintlayout:constraintlayout:${Versions.android_constraint_layout}"
        val cue =                               "com.fxn769:cue:${Versions.android_cue}"
        val design =                            "com.android.support:design:${Versions.android_support}"
        val espresso =                          "androidx.test.espresso:espresso-core:${Versions.android_espresso}"
        val exo_player =                        "com.google.android.exoplayer:exoplayer:${Versions.android_exo_player}"
        val exo_player_rtmp =                   "com.google.android.exoplayer:extension-rtmp:${Versions.android_exo_player}"
        val ktx =                               "androidx.core:core-ktx:${Versions.android_ktx}"
        val lifecycle_compiler =                "androidx.lifecycle:lifecycle-compiler:${Versions.android_lifecycle}"
        val lifecycle_extensions =              "androidx.lifecycle:lifecycle-extensions:${Versions.android_lifecycle}"
        val lifecycle_jdk8 =                    "androidx.lifecycle:lifecycle-common-java8:${Versions.android_lifecycle}"
        val lifecycle_runtime =                 "androidx.lifecycle:lifecycle-runtime:${Versions.android_lifecycle}"
        val lifecycle_viewmodel =               "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.android_lifecycle}"
        val material =                          "com.google.android.material:material:${Versions.android_material}"
        val material_bottom_bar =               "studio.forface.materialbottombar:materialbottombar:${Versions.android_material_bottom_bar}"
        val material_bottom_bar_navigation =    "studio.forface.materialbottombar:materialbottombar-navigation:${Versions.android_material_bottom_bar}"
        val navigation_fragment =               "android.arch.navigation:navigation-fragment-ktx:${Versions.android_navigation}"
        val navigation_ui =                     "android.arch.navigation:navigation-ui-ktx:${Versions.android_navigation}"
        val paging =                            "androidx.paging:paging-runtime-ktx:${Versions.android_paging}"
        val robolectric =                       "org.robolectric:robolectric:${Versions.android_robolectric}"
        val room =                              "androidx.room:room-runtime:${Versions.android_room}"
        val room_compiler =                     "androidx.room:room-compiler:${Versions.android_room}"
        val room_coroutines =                   "androidx.room:room-coroutines:${Versions.android_room}"
        val room_testing =                      "androidx.room:room-testing:${Versions.android_room}"
        val support_annotations =               "com.android.support:support-annotations:28.0.0"
        val test_core =                         "androidx.test:core:${Versions.android_test_core}"
        val test_fragment =                     "androidx.fragment:fragment-testing:${Versions.android_test_fragment}"
        val test_rules =                        "androidx.test:rules:${Versions.android_test_runner}"
        val test_runner =                       "androidx.test:runner:${Versions.android_test_runner}"
        val theia =                             "studio.forface.theia:theia:${Versions.android_theia}"
        val view_pager2 =                       "androidx.viewpager2:viewpager2:${Versions.android_view_pager2}"
        val view_state_store =                  "studio.forface.viewstatestore:viewstatestore:${Versions.android_view_state_store}"
        val view_state_store_paging =           "studio.forface.viewstatestore:viewstatestore-paging:${Versions.android_view_state_store}"
        val work =                              "android.arch.work:work-runtime-ktx:${Versions.android_work}"
    }
}