plugins {
    id("java")
    alias(libs.plugins.quarkus)
}

dependencies {
    implementation("io.quarkiverse.helm:quarkus-helm:1.2.3")
    implementation(enforcedPlatform(libs.quarkus.platform.core))
    implementation(enforcedPlatform(libs.quarkus.platform.operatorsdk))
    implementation("io.quarkiverse.operatorsdk:quarkus-operator-sdk")
    implementation("io.quarkus:quarkus-arc")

    implementation(project(":packages:kubernetes:model"))
    implementation(project(":packages:kubernetes:agones-model"))

    implementation(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation("io.quarkus:quarkus-junit5")
    implementation(project(":packages:common"))
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
