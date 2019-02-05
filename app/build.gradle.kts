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

    /* Android */
    implementation( Libs.Android.lifecycle_runtime )
    implementation( Libs.Android.lifecycle_viewmodel_ktx )
    implementation( Libs.Android.work )
    implementation( Libs.Android.work_ktx )
}
