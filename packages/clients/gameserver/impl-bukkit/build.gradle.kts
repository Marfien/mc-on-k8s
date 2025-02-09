plugins {
    id("java")
    id("mconk8s.replace-tokens")
    id("mconk8s.client-conventions")
    id("mconk8s.shade-conventions")
}

dependencies {
    implementation(project(":packages:clients:gameserver:agent"))
    implementation(libs.adventure.serializer.legacy)

    compileOnly(libs.gameserver.bukkit)
}
