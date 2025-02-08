plugins {
    id("java-library")
    id("mconk8s.client-conventions")
}

dependencies {
    api(libs.bundles.agones)
    api(libs.slf4j.api)
    api(libs.adventure.api)
    api(libs.kubernetes.client)

    api(project(":packages:common"))
    api(project(":packages:clients:client-api"))
}
