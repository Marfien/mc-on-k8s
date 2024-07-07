plugins {
    id("java-library")
}

dependencies {
    implementation(project(":packages:clients:proxy:proxy-agent"))
    compileOnly(libs.proxy.velocity)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
