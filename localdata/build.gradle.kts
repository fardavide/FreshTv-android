plugins {
    id("java-library" )
    id("kotlin" )
    id("com.squareup.sqldelight" )
}

dependencies {
    api( project(":domain") )

    applyTests()

    api( Libs.sqldelight )
}

sqldelight.packageName = "${Project.id}.localdata"