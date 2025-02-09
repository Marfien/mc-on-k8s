plugins {
    id("java")
    id("mconk8s.replace-tokens")
    id("mconk8s.java-conventions")
    alias(libs.plugins.quarkus)
}

dependencies {
    // imported platforms (imported pom dependency management in maven)
    implementation(enforcedPlatform(libs.quarkus.platform.core))
    implementation(enforcedPlatform(libs.quarkus.platform.operatorsdk))

    // operator-sdk dependencies
    implementation("io.quarkiverse.operatorsdk:quarkus-operator-sdk")
    implementation("io.quarkiverse.operatorsdk:quarkus-operator-sdk-bundle-generator")

    // quarkus dependencies
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-arc")

    // To ensure compatibility with k3s-based kubernetes distros (such as k3d, rancher)
    // https://javaoperatorsdk.io/docs/faq/#q-how-to-fix-sunsecurityprovidercertpathsuncertpathbuilderexception-on-rancher-desktop-and-k3dk3s-kubernetes
    implementation("org.bouncycastle:bcprov-jdk18on")
    implementation("org.bouncycastle:bcpkix-jdk18on")

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
