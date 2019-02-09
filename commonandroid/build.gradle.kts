plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("io.fabric" )
}

crashlytics {
    manifestPath = "$buildDir/intermediates/library_manifest/debug/AndroidManifest.xml"
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
    api( Libs.Android.navigation_fragment )
    api( Libs.Android.navigation_ui )
    implementation( Libs.Android.picasso )
    api( Libs.Android.work )
}