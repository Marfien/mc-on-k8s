plugins {
    id("maven-publish")
    id("mc-on-k8s.java-conventions")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/octocat/hello-world")
            credentials {
                username = providers.systemProperty("publish.user").getOrElse("none")
                password = providers.systemProperty("publish.password").getOrElse("none")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}