plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )
    api( project(":androiddatabase" ) )
    //implementation( project(":androiddatabase:room" ) )
    implementation( project(":androiddatabase:sqldelight" ) )
    api( project(":commonandroid" ) )
    implementation( project(":localdata" ) )
    //implementation( project(":localdata:room" ) )
    implementation( project(":localdata:sqldelight" ) )
    implementation( project(":parsers" ) )
    api( project(":player" ) )
    api( project(":preferences" ) )
    implementation( project(":settings" ) )

    applyTests()
}