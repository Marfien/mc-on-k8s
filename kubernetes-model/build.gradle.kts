plugins {
    id("mc-on-k8s.java-publish-conventions")
    id("java-library")
}

dependencies {
    api(libs.kubernetes.client)
}