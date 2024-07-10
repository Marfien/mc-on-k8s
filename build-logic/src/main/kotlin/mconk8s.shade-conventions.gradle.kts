import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import gradle.kotlin.dsl.accessors._8c47cae829ea3d03260d5ff13fb2398e.build

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

tasks.build {
    dependsOn("shadowJar")
}
