plugins {
    id("java")
}

repositories {
    maven {
        name = "spigot-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(project(":server-api"))
    implementation(libs.server.spigot)
}