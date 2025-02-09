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
        ":packages:agones-crds",
        ":packages:crds",
        ":packages:operator"
)

include(
        "packages:clients:api"
)

include(
        ":packages:clients:proxy:agent",
        ":packages:clients:proxy:impl-bungeecord",
        ":packages:clients:proxy:impl-velocity"
)

include(
        ":packages:clients:gameserver:agent",
        ":packages:clients:gameserver:impl-bukkit",
        ":packages:clients:gameserver:impl-sponge",
        ":packages:clients:gameserver:impl-minestom"
)
