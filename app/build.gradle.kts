plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
}

android {
    compileSdkVersion( Project.targetSdk )
    defaultConfig {
        applicationId = Project.id
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
    implementation( project(":dimodules" ) )
    implementation( project(":commonandroid" ) )

    applyTests()
    applyAndroidTests()

    implementation( Libs.koin_android )
    implementation( Libs.koin_android_viewmodel )

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
}
