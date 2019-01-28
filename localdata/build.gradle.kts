plugins {
    id("java-library" )
    id("kotlin" )
    id("com.squareup.sqldelight" )
}

dependencies {
    implementation( project(":domain") )
    implementation( Libs.sqldelight )
}

sqldelight.packageName = "${Project.id}.localdata"