dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "minecraft-on-k8s"

include(
        ":packages:kubernetes:agones-model",
        ":packages:kubernetes:model",
        ":packages:kubernetes:operator"
)

include(
        ":packages:clients:proxy:proxy-api",
        ":packages:clients:proxy:proxy-impl-bungeecord",
        ":packages:clients:proxy:proxy-impl-velocity"
)

include(
        ":packages:clients:gameserver:gameserver-api",
        ":packages:clients:gameserver:gameserver-impl-bukkit",
        ":packages:clients:gameserver:gameserver-impl-sponge",
        ":packages:clients:gameserver:gameserver-impl-minestom"
)
