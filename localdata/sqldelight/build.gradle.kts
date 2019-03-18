import com.squareup.sqldelight.gradle.SqlDelightDatabase

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

sqldelight {
    methodMissing("Database", arrayOf( closureOf<SqlDelightDatabase> {
        packageName = "${Project.id}.localdata.sqldelight"
    } ) )
}