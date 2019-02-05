plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android {
    compileSdkVersion( Project.targetSdk )
    defaultConfig {
        minSdkVersion( Project.minSdk )
        targetSdkVersion( Project.targetSdk )
        versionCode = Project.versionCode
        versionName = Project.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release" ) {
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile( "proguard-android.txt" ), "proguard-rules.pro" )
        }
    }
    packagingOptions.exclude("META-INF/main.kotlin_module" )
}

dependencies {
    api( project(":domain") )

    applyTests()
    applyAndroidTests()

    /* Kotlin */
    api( Libs.coroutines_android )
    api( Libs.koin_android )
    api( Libs.koin_android_viewmodel )

    /* Android */
    api( Libs.Android.appcompat )
    api( Libs.Android.constraint_layout )
    implementation( Libs.Android.cue )
    api( Libs.Android.ktx )
    implementation( Libs.Android.picasso )
}