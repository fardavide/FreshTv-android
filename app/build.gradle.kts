plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("androidx.navigation.safeargs.kotlin" )
    id("io.fabric" )
}

android { applyAndroidConfig( Project.id ) }

crashlytics {
    manifestPath = "$buildDir/intermediates/merged_manifests/release/AndroidManifest.xml"
}

dependencies {
    implementation( project(":dimodules" ) )

    applyTests()
    applyAndroidTests()

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
    implementation( Libs.Android.lifecycle_viewmodel )
    implementation( Libs.Android.material_bottom_bar_navigation )

    /* Tools */
    testCompileOnly( Libs.Android.fastlane_screengrab )
}

apply( plugin = "com.google.gms.google-services" )