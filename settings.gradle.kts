pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}

rootProject.name = "minecraft-on-k8s"

include(":packages:kubernetes:agones-model")
include(":packages:kubernetes:model")
include(":packages:kubernetes:operator")
include(":packages:clients:proxy:proxy-api")
