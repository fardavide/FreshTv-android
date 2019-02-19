plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )
    implementation( project(":androiddatabase" ) )
    //implementation( project(":androiddatabase:room" ) )
    implementation( project(":androiddatabase:sqldelight" ) )
    implementation( project(":commonandroid" ) )
    implementation( project(":localdata" ) )
    //implementation( project(":localdata:room" ) )
    implementation( project(":localdata:sqldelight" ) )
    implementation( project(":parsers" ) )
    implementation( project(":settings" ) )

    applyTests()
}