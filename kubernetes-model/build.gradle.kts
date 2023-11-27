plugins {
    id("java")
    id("java-library")
}

dependencies {
    api(libs.kubernetes.client)
}