plugins {
    id("java-library")
    id("org.kordamp.gradle.jandex") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kubernetes.client)
    api(project(":agones-model"))
    annotationProcessor(libs.kubernetes.crdsgen)
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
