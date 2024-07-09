dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }
}

rootProject.name = "minecraft-on-k8s"

includeBuild("build-logic")
include(":packages:common")

include(
        ":packages:kubernetes:agones-model",
        ":packages:kubernetes:model",
        ":packages:kubernetes:operator"
)

include(
        "packages:clients:client-common"
)

include(
        ":packages:clients:proxy:proxy-agent",
        ":packages:clients:proxy:proxy-impl-bungeecord",
        ":packages:clients:proxy:proxy-impl-velocity"
)

include(
        ":packages:clients:gameserver:gameserver-agent",
        ":packages:clients:gameserver:gameserver-impl-bukkit",
        ":packages:clients:gameserver:gameserver-impl-sponge",
        ":packages:clients:gameserver:gameserver-impl-minestom"
)
