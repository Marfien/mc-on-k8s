plugins {
    id("mc-on-k8s.java-conventions")
}

repositories {
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(project(":server-api"))
    compileOnly(libs.server.minestom)
}