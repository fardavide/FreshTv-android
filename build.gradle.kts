// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories( repos )
    dependencies( classpathDependencies )
}


allprojects {
    repositories( repos )
}

tasks.register( "clean", Delete::class.java ) {
    delete( rootProject.buildDir )
}
