plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("androidx.navigation.safeargs" )
}

android { applyAndroidConfig( Project.id ) }

dependencies {
    implementation( project(":dimodules" ) )
    implementation( project(":commonandroid" ) )
    implementation( project(":player" ) )

    applyTests()
    applyAndroidTests()

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
    implementation( Libs.Android.lifecycle_viewmodel_ktx )
    implementation( Libs.Android.material_bottom_bar )
}