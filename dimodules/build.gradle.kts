plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )

    api( project(":about" ) )
    api( project(":androiddatabase" ) )
    api( project(":commonandroid" ) )
    api( project(":player" ) )
    api( project(":preferences" ) )

    //implementation( project(":androiddatabase:room" ) )
    implementation( project(":androiddatabase:sqldelight" ) )
    implementation( project(":localdata" ) )
    //implementation( project(":localdata:room" ) )
    implementation( project(":localdata:sqldelight" ) )
    implementation( project(":parsers" ) )
    implementation( project(":settings" ) )

    applyTests()
}