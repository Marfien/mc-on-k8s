plugins {
    id("java-library")
    // There is literlly no reason to use an old version of Java on a proxy
    // they do not contain any game play logic which would be the only reason to use an old version of Java/Minecraft
    id("mconk8s.java-conventions")
}

dependencies {
    implementation(project(":packages:kubernetes:model"))
    api(project(":packages:clients:client-common"))
}
