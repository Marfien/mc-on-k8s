plugins {
    id("java-library")
}

dependencies {
    implementation(project(":packages:clients:proxy:proxy-agent"))
    implementation(libs.adventure.serializer.bungeecord)

    compileOnly(libs.proxy.bungeecord)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
