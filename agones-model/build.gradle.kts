plugins {
    id("java-library")
}

group = "dev.marfien.minecraftonk8s"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kubernetes.client)
}

javaToolchains {
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}