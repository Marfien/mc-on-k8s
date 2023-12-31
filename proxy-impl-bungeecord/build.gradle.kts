plugins {
    id("mc-on-k8s.java-fat-conventions")
}

repositories {
    maven {
        name = "bungeecord-repo"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        name = "minecraft-repo"
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    implementation(project(":proxy-api"))
    compileOnly(libs.proxy.bungeecord)
    implementation(libs.adventure.bungeecord)
}