import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("java")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
    alias(libs.plugins.sponge)
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
    license("")
    plugin("minecraftonk8s-agent") {
        displayName("MinecraftOnK8s Agent")
        version(project.version.toString())
        entrypoint("dev.marfien.minecraftonk8s.client.gameserver.sponge.SpongeAgentPlugin")
        description("MinecraftOnK8s Agent for Sponge")
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}
