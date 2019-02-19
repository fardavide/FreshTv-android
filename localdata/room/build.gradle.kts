plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlin-kapt" )
}
base.archivesBaseName = "localdata-room"

android { applyAndroidConfig() }

dependencies {
    api( project(":localdata") )

    applyTests()

    api( Libs.Android.room )
    kapt( Libs.Android.room_compiler )
    api( Libs.Android.room_coroutines )
    api( Libs.Android.paging )
    androidTestImplementation( Libs.Android.room_testing )
}
