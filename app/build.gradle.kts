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
    packagingOptions {
        exclude("META-INF/DEPENDENCIES" )
        exclude("META-INF/LICENSE" )
        exclude("META-INF/LICENSE.txt" )
        exclude("META-INF/license.txt" )
        exclude("META-INF/NOTICE" )
        exclude("META-INF/NOTICE.txt" )
        exclude("META-INF/notice.txt" )
        exclude("META-INF/ASL2.0" )
        exclude("META-INF/ktor-http.kotlin_module" )
        exclude("META-INF/kotlinx-io.kotlin_module" )
        exclude("META-INF/atomicfu.kotlin_module" )
        exclude("META-INF/ktor-utils.kotlin_module" )
        exclude("META-INF/kotlinx-coroutines-io.kotlin_module" )
        exclude("META-INF/ktor-client-json.kotlin_module" )
        exclude("META-INF/ktor-client-logging.kotlin_module" )
        exclude("META-INF/ktor-client-core.kotlin_module" )
    }
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
