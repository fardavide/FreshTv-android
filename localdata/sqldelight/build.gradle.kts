plugins {
    id("java-library" )
    id("kotlin" )
    id("com.squareup.sqldelight" )
}
base.archivesBaseName = "localdata-sqldelight"

dependencies {
    api( project(":localdata") )

    applyTests()

    api( Libs.sqldelight )
}

sqldelight.packageName = "${Project.id}.localdata.sqldelight"