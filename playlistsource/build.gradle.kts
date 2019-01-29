plugins {
    id("java-library" )
    id("kotlin" )
  }

dependencies {
    implementation( project(":domain") )

    applyTests()

    /* Test */
    testImplementation( Libs.test )
    testImplementation( Libs.test_junit )
    testImplementation( Libs.mockk )
}