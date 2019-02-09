plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("androidx.navigation.safeargs.kotlin" )
}

android { applyAndroidConfig() }

dependencies {
    implementation( project(":commonandroid" ) )

    applyTests()
    applyAndroidTests()

    /* Android */
    implementation( Libs.Android.exo_player )
}