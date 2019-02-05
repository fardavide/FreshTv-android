plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )
    implementation( project(":androiddatabase" ) )
    implementation( project(":commonandroid" ) )
    implementation( project(":localdata" ) )
    implementation( project(":parsers" ) )

    applyTests()
}