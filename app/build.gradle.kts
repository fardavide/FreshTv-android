plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("androidx.navigation.safeargs.kotlin" )
    id("io.fabric" )
}

private val appName = "FreshTv"

android {
    applyAndroidConfig( Project.id )
    libraryVariants.all {

    }
}

dependencies {
    implementation( project(":dimodules" ) )

    applyTests()
    applyAndroidTests()

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
    implementation( Libs.Android.lifecycle_viewmodel )
    implementation( Libs.Android.material_bottom_bar_navigation )
}

apply( plugin = "com.google.gms.google-services" )