plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("com.squareup.sqldelight" )
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

    /* Kotlin */
    implementation( Libs.kotlin )
    implementation( Libs.coroutines )
    implementation( Libs.coroutines_android )
    implementation( Libs.serialization )

    /* Test */
    testImplementation( Libs.test )
    testImplementation( Libs.test_junit )
    testImplementation( Libs.mockk )

    /* Android */
    implementation( Libs.Android.appcompat )
    implementation( Libs.Android.ktx )

    /* Android Test */
    androidTestImplementation( Libs.Android.test_runner )
    androidTestImplementation( Libs.Android.espresso )
}
