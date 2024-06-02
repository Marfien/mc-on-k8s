plugins {
    id("java-library")
    id("org.kordamp.gradle.jandex") version "2.0.0"
}

group = "dev.marfien.minecraftonk8s"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.bundles.annotations)
    annotationProcessor(libs.bundles.annotations)

    api(libs.kubernetes.client)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {

    val deleteTask = register<Delete>("deleteGeneratedFabric8Builder") {
        val buildDir = project.layout.buildDirectory
        val pkg = "io.fabric8".replace('.', '/')
        delete(buildDir.dir("generated/sources/annotationProcessor/java/main/${pkg}"))
        delete(buildDir.dir("classes/java/main/${pkg}"))
    }

    compileJava {
        //finalizedBy(deleteTask)
    }

}
