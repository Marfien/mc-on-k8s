import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.provideDelegate

plugins {
    id("java")
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
