import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

apply<ShadowPlugin>()

tasks.withType<ShadowJar> {
    this.mergeServiceFiles()
    this.isEnableRelocation = true
    this.minimize {
        // Exclude those tons of classes if they aren't needed
        include(project(":packages:kubernetes:model"))
        include(project(":packages:kubernetes:agones-model"))
    }
}
