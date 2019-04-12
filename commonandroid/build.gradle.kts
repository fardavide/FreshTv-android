plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( project(":domain") )

    applyTests()
    applyAndroidTests()
    //testImplementation( Libs.threeten_bp ) { exclude("com.jakewharton.threetenabp","threetenabp" ) }

    /* Kotlin */
    api( Libs.coroutines_android )

    /* Other */
    api( Libs.firebase_core_android )
    api( Libs.firebase_crashlytics_android )
    api( Libs.koin_android )
    api( Libs.koin_android_viewmodel )
    api( Libs.timber_android )

    /* Android */
    api( Libs.Android.appcompat )
    api( Libs.Android.constraint_layout )
    implementation( Libs.Android.cue )
    api( Libs.Android.design )
    api( Libs.Android.ktx )
    api( Libs.Android.material )
    api( Libs.Android.material_bottom_bar )
    api( Libs.Android.navigation_fragment )
    api( Libs.Android.navigation_ui )
    api( Libs.Android.paging )
    api( Libs.Android.theia )
    api( Libs.Android.view_pager2 )
    api( Libs.Android.view_state_store )
    api( Libs.Android.view_state_store_paging )
    api( Libs.Android.work )
}