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
    api( Libs.threeten_bp )
}