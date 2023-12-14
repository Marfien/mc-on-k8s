plugins {
    id("mc-on-k8s.java-fat-conventions")
    id("mc-on-k8s.replace-tokens")
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