dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "minecraft-on-k8s"

include(
        ":packages:kubernetes:agones-model",
        ":packages:kubernetes:model",
        ":packages:kubernetes:operator"
)

include(
        "packages:clients:client-api",
        "packages:clients:client-common"
)

include(
        ":packages:clients:proxy:proxy-agent",
        ":packages:clients:proxy:proxy-impl-bungeecord",
        ":packages:clients:proxy:proxy-impl-velocity"
)

include(
        ":packages:clients:gameserver:gameserver-api",
        ":packages:clients:gameserver:gameserver-impl-bukkit",
        ":packages:clients:gameserver:gameserver-impl-sponge",
        ":packages:clients:gameserver:gameserver-impl-minestom"
)
