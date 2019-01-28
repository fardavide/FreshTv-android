plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
    id("kotlinx-serialization" )
    id("com.squareup.sqldelight" )
}

android {
    compileSdkVersion( 28 )
    defaultConfig {
        applicationId = "studio.forface.freshtv"
        minSdkVersion( 21 )
        targetSdkVersion( 28 )
        versionCode = 1
        versionName = "1.0"
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
    implementation( Libs.android_appcompat )
    implementation( Libs.android_ktx )

    /* Android Test */
    androidTestImplementation( Libs.android_test_runner )
    androidTestImplementation( Libs.android_espresso )
}
