plugins {
    id("java-library")
}

dependencies {
    implementation(project(":packages:clients:proxy:proxy-agent"))
    compileOnly(libs.proxy.bungeecord)
    implementation(libs.adventure.serializer.bungeecord)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
