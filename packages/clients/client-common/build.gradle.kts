plugins {
    id("java-library")
}

dependencies {
    api(libs.bundles.agones)
    api(project(":packages:clients:client-api"))
    api(libs.slf4j.api)
    api(libs.adventure.api)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
