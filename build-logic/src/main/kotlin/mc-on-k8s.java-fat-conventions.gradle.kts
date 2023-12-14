import com.github.jengelman.gradle.plugins.shadow.ShadowJavaPlugin
import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("mc-on-k8s.java-conventions")
}

apply<ShadowPlugin>()

tasks {
    named<ShadowJar>(ShadowJavaPlugin.SHADOW_JAR_TASK_NAME).configure {
        this.mergeServiceFiles()
    }
}