plugins {
    id("java-library" )
    id("kotlin" )
    id("com.squareup.sqldelight" )
}

dependencies {
    api( project(":domain") )
    api( Libs.sqldelight )
}

sqldelight.packageName = "${Project.id}.localdata"