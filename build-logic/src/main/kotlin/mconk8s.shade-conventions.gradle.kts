import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

apply<ShadowPlugin>()

tasks.withType<ShadowJar> {
    this.mergeServiceFiles()
    this.isEnableRelocation = true
    this.minimize {
        // Exclude those tons of classes if they aren't needed
        include(project(":packages:crds"))
        include(project(":packages:agones-crds"))
    }
}

tasks.named("build") {
    dependsOn("shadowJar")
}
