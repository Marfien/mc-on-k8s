plugins {
    id("java-library")
    id("org.kordamp.gradle.jandex") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.bundles.annotations)
    annotationProcessor(libs.bundles.annotations)
    // https://github.com/sundrio/sundrio/issues/104#issuecomment-460770907
    annotationProcessor(libs.kubernetes.client)

    api(libs.kubernetes.client)
    api(project(":packages:kubernetes:agones-model"))
    annotationProcessor(libs.kubernetes.crdsgen)
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
