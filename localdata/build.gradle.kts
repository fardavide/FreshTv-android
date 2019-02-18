plugins {
    id("java-library" )
    id("kotlin" )
}

dependencies {
    api( project(":domain") )

    applyTests()
}