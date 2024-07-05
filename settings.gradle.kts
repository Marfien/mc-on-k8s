pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "minecraft-on-k8s"

include(":packages:kubernetes:agones-model")
include(":packages:kubernetes:model")
include(":packages:kubernetes:operator")

include(":packages:clients:proxy:proxy-api")
