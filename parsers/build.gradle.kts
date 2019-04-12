plugins {
    id("java-library" )
    id("kotlin" )
}

dependencies {
    implementation( project(":domain") )
    applyTests()

    implementation( Libs.ktor )
    implementation( Libs.ktor_android )
}