plugins {
    id("java-library")
}

dependencies {
    implementation(libs.kubernetes.client)
    implementation(project(":packages:kubernetes:model"))
    api(project(":packages:clients:client-common"))
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
