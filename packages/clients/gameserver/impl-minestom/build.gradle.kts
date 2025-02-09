plugins {
    id("java")
    id("mconk8s.replace-tokens")
    // Do not use the client-conventions plugin here, as minestom has a rolling release cycle
    // It is not really worth it to support older versions of java for it
    // id("mconk8s.client-conventions")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
}

dependencies {
    implementation(project(":packages:clients:gameserver:agent"))

    compileOnly(libs.gameserver.minestom) {
        exclude("*")
    }
}
