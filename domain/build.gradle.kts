plugins {
    id("java-library" )
    id("kotlin" )
}

dependencies {

    applyTests()

    /* Kotlin */
    api( Libs.kotlin )
    api( Libs.coroutines )
    implementation( Libs.reflect )

    /* Other */
    api( Libs.threeten_bp )
}