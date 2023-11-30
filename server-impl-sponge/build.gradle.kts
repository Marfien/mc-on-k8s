plugins {
    id("mc-on-k8s.java-conventions")
}

repositories {
    maven {
        name = "sponge-repo"
        url = uri("https://repo.spongepowered.org/repository/maven-public")
    }
}

dependencies {
    implementation(project(":server-api"))
    compileOnly(libs.server.sponge)
}