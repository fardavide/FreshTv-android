plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("androidx.navigation.safeargs.kotlin" )
}

android { applyAndroidConfig( Project.id ) }

dependencies {
    implementation( project(":dimodules" ) )

    applyTests()
    applyAndroidTests()

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
    implementation( Libs.Android.lifecycle_viewmodel )
    implementation( Libs.Android.material_bottom_bar_navigation )
    implementation( Libs.Android.view_state_store_paging )
}