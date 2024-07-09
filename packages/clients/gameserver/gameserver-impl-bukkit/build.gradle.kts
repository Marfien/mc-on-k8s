plugins {
    id("java")
    id("mconk8s.replace-tokens")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
}

dependencies {
    implementation(project(":packages:clients:gameserver:gameserver-agent"))
    implementation(libs.adventure.serializer.legacy)

    compileOnly(libs.gameserver.bukkit)
}
