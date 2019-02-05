plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )
    implementation( project(":localdata" ) )

    applyTests()
    applyAndroidTests()

    implementation( Libs.koin_android )
    implementation( Libs.sqldelight_android_driver )
}