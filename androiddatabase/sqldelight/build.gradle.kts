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
    implementation( project(":androiddatabase" ) )
    implementation( project(":localdata:sqldelight" ) )

    applyTests()
    applyAndroidTests()

    implementation( Libs.sqldelight_android_driver )
    implementation( Libs.sqldelight_android_paging )
}