plugins {
    id("java-library")
}

dependencies {
    api(libs.bundles.agones)
    api(libs.slf4j.api)
    api(libs.adventure.api)
    api(libs.kubernetes.client)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
