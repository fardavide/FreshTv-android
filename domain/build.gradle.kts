plugins {
    id("java-library" )
    id("kotlin" )
}

dependencies {

    /* Kotlin */
    api( Libs.kotlin )
    api( Libs.coroutines )
    implementation( Libs.reflect )

    /* Test */
    testApi( Libs.test )
    testApi( Libs.test_junit )
    testApi( Libs.mockk )

    /* Other */
    api( Libs.threeten_bp )
}