import org.gradle.kotlin.dsl.testImplementation

plugins {
    id("java-library" )
    id("kotlin" )
}

dependencies {

    applyTests()

    /* Kotlin */
    api( Libs.kotlin )
    api( Libs.coroutines )
    api( Libs.reflect )

    /* Other */
    api( Libs.koin )
    api( Libs.threeten_android_bp )
    testImplementation( Libs.threeten_bp ) {
        exclude( group = "com.jakewharton.threetenabp", module = "threetenabp" )
    }
}