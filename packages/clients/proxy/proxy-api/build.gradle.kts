plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
