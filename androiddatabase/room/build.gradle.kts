plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    implementation( project(":androiddatabase" ) )
    implementation( project(":localdata:room" ) )

    applyTests()
    applyAndroidTests()

    androidTestImplementation( Libs.Android.room_testing )
}