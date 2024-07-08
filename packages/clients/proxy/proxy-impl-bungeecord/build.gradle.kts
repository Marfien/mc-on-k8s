plugins {
    id("java")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
}

dependencies {
    implementation(project(":packages:clients:proxy:proxy-agent"))
    implementation(libs.adventure.serializer.bungeecord)

    compileOnly(libs.proxy.bungeecord)
}
