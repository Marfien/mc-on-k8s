plugins {
    id("java-library")
}

dependencies {
    api(libs.bundles.agones)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
