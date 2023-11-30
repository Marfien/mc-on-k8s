plugins {
    id("mc-on-k8s.java-publish-conventions")
    id("java-library")
}

dependencies {
    api(project(":gameserver-base-api"))
    api(libs.adventure.api)
    api(project(":kubernetes-model"))
}