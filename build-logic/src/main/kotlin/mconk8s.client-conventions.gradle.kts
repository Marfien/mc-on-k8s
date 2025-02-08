plugins {
    id("java")
    id("mconk8s.java-conventions")
}

val javaVersion: String by project
val javaVersionByProject = javaVersion.toInt()

// Something to multi release jars:
// Add classes for specific java versions to the jar
// and they will be used instead of the default ones
// if the java version is supported

// Create a new source set for the latest java version
val javaLatestSourceSet = sourceSets.create("javaLatest") {
    java.srcDir("src/javaLatest")
}

// Let the latest java version source set contain all dependencies from the main source set
configurations["javaLatestImplementation"].extendsFrom(configurations.implementation.get())
// configurations["javaLatestCompileOnly"].extendsFrom(configurations.compileOnly.get())

tasks {
    // Compilation needs to be treated separately
    // Otherwise, there will be conflicts in dependency resolution
    val compileJavaLatestJava by getting(JavaCompile::class) {
        source(javaLatestSourceSet.java)
        classpath = javaLatestSourceSet.compileClasspath
        destinationDirectory.set(layout.buildDirectory.dir("classes/java/${javaLatestSourceSet.name}"))
        options.release.set(javaVersionByProject)
    }

    // Attach the latest java version classes to the multi-release jar
    val processJavaLatestClasses by registering(Copy::class) {
        dependsOn(compileJavaLatestJava)
        from(javaLatestSourceSet.output)
        into(layout.buildDirectory.dir("classes/java/main/META-INF/versions/${javaVersionByProject}"))
    }

    // Not quite sure why this is needed, but it is
    // Has smth to do with the output path of processJavaLatestClasses
    // But that should be the ouput path of compileJava as well, so idk
    compileJava {
        dependsOn(processJavaLatestClasses)
    }

    withType<Jar>().configureEach {
        dependsOn(processJavaLatestClasses)
        manifest {
            attributes["Multi-Release"] = "true"
        }
    }

    // Set the global supportet java version
    withType<JavaCompile>().configureEach {
        if (this.name != compileJavaLatestJava.name) {
            val minimumSupportedJavaVersion: String by project
            options.release.set(minimumSupportedJavaVersion.toInt())
        }
    }
}
