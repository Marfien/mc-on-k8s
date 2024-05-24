plugins {
    id("java-library")
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
