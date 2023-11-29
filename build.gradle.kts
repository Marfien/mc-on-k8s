import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("maven-publish")
}

group = "dev.marfien.minecraftonk8s"

repositories {
    mavenCentral()
}

// Don't build a jar for parent project
tasks {
    jar {
        enabled = false
    }
}

// Configuration of subprojects

project.subprojects {
    this.group = this.rootProject.group
    this.version = this.rootProject.version

    repositories {
        mavenCentral()
    }

    afterEvaluate {
        if (plugins.hasPlugin("java")) {
            this.applyJava()
        }

        if (plugins.hasPlugin("maven-deploy")) {
            this.applyPublishing()
        }

        tasks {
            findByName("replaceTokens")?.let {
                named<Copy>(it.name) {
                    this.applyTokenFilter()
                }
            }

            processResources {
                this.applyTokenFilter()
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

fun Project.applyPublishing() = publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/octocat/hello-world")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

fun Copy.applyTokenFilter() {
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