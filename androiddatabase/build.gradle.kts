plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android {
    applyAndroidConfig()
    configurations.all {
        resolutionStrategy.force( Libs.Android.annotation )
        resolutionStrategy.force( Libs.Android.androidx_core )
    }
}

dependencies {
    api( project(":domain") )
    implementation( project(":localdata" ) )

    applyTests()
    applyAndroidTests()

    api( Libs.koin_android )
    implementation( Libs.Android.paging )
}