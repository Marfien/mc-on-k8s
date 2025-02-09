plugins {
    id("java")
    id("mconk8s.replace-tokens")
    id("mconk8s.java-conventions")
    alias(libs.plugins.quarkus)
}

dependencies {
    implementation(enforcedPlatform(libs.quarkus.platform.core))
    implementation(enforcedPlatform(libs.quarkus.platform.operatorsdk))
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkiverse.operatorsdk:quarkus-operator-sdk")
    implementation("io.quarkus:quarkus-arc")

    implementation(project(":packages:common"))
    implementation(project(":packages:crds"))
    implementation(project(":packages:agones-crds"))

    testImplementation("io.quarkus:quarkus-junit5")
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks {
    val copyCRDs = register<Copy>("copyCRDs") {
        group = "helm"
        description = "Copys the generated CRDs to the Helm chart"

        from(project.layout.buildDirectory.dir("kubernetes")) {
            include("*mconk8s.marfien.dev*.yml")
        }
        into(project.layout.buildDirectory.dir("helm/crds"))

        // quarkusAppPartsBuild generates the CRDs
        dependsOn("quarkusAppPartsBuild")
    }

    val genHelmChart = register<ProcessResources>("generateHelmChart") {
        group = "helm"
        description = "Generates Helm chart from Helm template files"

        from(project.layout.projectDirectory.dir("src/main/helm"))
        into(project.layout.buildDirectory.dir("helm"))
        finalizedBy(copyCRDs)
    }

    build {
        dependsOn(genHelmChart)
    }
}
