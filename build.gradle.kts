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
