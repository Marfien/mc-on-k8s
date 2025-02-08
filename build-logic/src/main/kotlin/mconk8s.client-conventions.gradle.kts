plugins {
    id("java")
    id("mconk8s.java-conventions")
}

// The compile release version needs to be set to the minimum supported Java version
// to ensure compatibility with the target runtime environment.
tasks.withType<JavaCompile>().configureEach {
    val minimumSupportedJavaVersion: String by project
    options.release.set(minimumSupportedJavaVersion.toInt())
}
