import com.android.build.gradle.TestedExtension

fun TestedExtension.applyAndroidConfig(applicationId: String? = null ) {
    compileSdkVersion( Project.targetSdk )
    defaultConfig {
        applicationId?.let { this.applicationId = it }
        minSdkVersion( Project.minSdk )
        targetSdkVersion( Project.targetSdk )
        versionCode = Project.versionCode
        versionName = Project.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release" ) {
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile("proguard-android.txt" ), "proguard-rules.pro" )
        }
        getByName("debug" ) {
            matchingFallbacks = listOf( "release" ) // TODO https://github.com/russhwolf/multiplatform-settings/issues/16
        }
    }
    compileOptions {
        sourceCompatibility = Project.jdkVersion
        targetCompatibility = Project.jdkVersion
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
        exclude("org/threeten/bp/format/ChronologyText.properties" )
    }
}