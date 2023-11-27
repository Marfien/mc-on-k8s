plugins {
    id("java")
    id("java-library")
}

dependencies {
    api(project(":gameserver-base-api"))
    api(libs.adventure.api)
    api(project(":kubernetes-model"))
}