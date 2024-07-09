import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("java")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
    id("org.spongepowered.gradle.plugin") version "2.0.2"
}

dependencies {
    implementation(project(":packages:clients:gameserver:gameserver-agent"))
}

sponge {
    apiVersion("8.0.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    license("MIT")
    plugin("minecraftonk8s-agent") {
        displayName("MinecraftOnK8s Agent")
        version("0.1")
        entrypoint("dev.marfien.minecraftonk8s.client.gameserver.sponge.SpongeAgentPlugin")
        description("MinecraftOnK8s Agent for Sponge")
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}
