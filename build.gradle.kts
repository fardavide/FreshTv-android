// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories( repos )
    dependencies( classpathDependencies )
}


allprojects {
    repositories( repos )
}

subprojects {
    tasks.withType( org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile::class ).forEach { task ->
        task.setProperty( "task.kotlinOptions.freeCompilerArgs", true )
    }
}

tasks.register("clean", Delete::class.java ) {
    delete( rootProject.buildDir )
}

tasks.register("versionCode" ) {
    println( Project.versionCode )
}

tasks.register("versionName" ) {
    println( Project.versionName )
}

tasks.whenTaskAdded {
    if ( name.startsWith("bundle" ) ) {
        val renameTaskName = "rename${name.capitalize()}Aab"
        val flavor = name.subSequence( "bundle".length, name.length - 1 ).toString().decapitalize()
        tasks.create( renameTaskName, Copy::class.java ) {
            val path = "$buildDir/outputs/bundle/$flavor/"
            from( path )
            include("app.aab" )
            destinationDir = File( "$buildDir/outputs/renamedBundle/" )
            rename("app.aab","FreshTv-${Project.versionName}-$flavor.aab" )
        }

        finalizedBy( renameTaskName )
    }
}
