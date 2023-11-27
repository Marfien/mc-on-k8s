import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
}

group = "dev.marfien.minecraftonk8s"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

project.subprojects {
    this.group = this.rootProject.group
    this.version = this.rootProject.version

    repositories {
        mavenCentral()
    }

    afterEvaluate {
        if (this.plugins.hasPlugin("java")) {
            this.applyJava()
        }

        tasks {
            val generateSourcesTask = register<Copy>("resolveTokens") {
                from(sourceSets.main.get().allSource.asPath)
                into("$buildDir/generated/sources/resolved")
                filter<ReplaceTokens>(
                        "tokens" to mapOf(
                                "project.version" to project.version.toString(),
                                "project.name" to (project.displayName),
                                "project.group" to project.group.toString(),
                                "project.id" to project.name,
                                "project.description" to (project.description ?: "none")
                        )
                )
            }

            compileJava.configure {
                setSource("$buildDir/generated/sources/resolve")
                dependsOn(generateSourcesTask)
            }
        }
    }
}

fun Project.applyJava() {
    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
        }

        test {
            useJUnitPlatform()
        }
    }
}