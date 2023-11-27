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