plugins {
    id("java-library")
    id("mconk8s.client-conventions")
}

dependencies {
    api(project(":packages:clients:client-common"))
}
