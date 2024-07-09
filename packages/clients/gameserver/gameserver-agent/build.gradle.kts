plugins {
    id("java-library")
    id("mconk8s.java-conventions")
}

dependencies {
    api(project(":packages:clients:client-common"))
}
