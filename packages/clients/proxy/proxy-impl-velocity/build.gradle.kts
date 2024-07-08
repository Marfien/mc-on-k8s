plugins {
    id("java")
    id("mconk8s.java-conventions")
    id("mconk8s.shade-conventions")
}

dependencies {
    implementation(project(":packages:clients:proxy:proxy-agent"))
    compileOnly(libs.proxy.velocity)
    annotationProcessor(libs.proxy.velocity)
}
