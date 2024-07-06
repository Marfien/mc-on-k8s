plugins {
    id("java-library")
}

dependencies {
    api(project(":packages:clients:client-common"))
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
