plugins {
    id("java-library")
    id("mconk8s.java-conventions")
}

dependencies {
    implementation(libs.kubernetes.client)
    implementation(project(":packages:kubernetes:model"))
    api(project(":packages:clients:client-common"))
}
