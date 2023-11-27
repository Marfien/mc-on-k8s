import org.apache.tools.ant.filters.ReplaceTokens
import java.util.*

plugins {
    id("java")
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(project(":proxy-api"))
    compileOnly(libs.proxy.velocity)
    annotationProcessor(libs.proxy.velocity)
}

tasks {
    val generateSourcesTask = register<Copy>("resolveTokens") {
        from(sourceSets.main.get().allSource.asPath)
        into("$buildDir/generated/sources/resolved")
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "project.version" to project.version.toString(),
                "project.name" to project.name,
                "project.group" to project.group.toString(),
                "project.id" to project.name,
                "project.description" to project.description
            )
        )
    }

    compileJava.configure {
        setSource("$buildDir/generated/sources/resolve")
        dependsOn(generateSourcesTask)
    }
}