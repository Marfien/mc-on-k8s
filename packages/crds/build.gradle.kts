plugins {
    id("java-library")
    id("mconk8s.java-conventions")
    alias(libs.plugins.jandex)
}

dependencies {
    compileOnly(libs.bundles.annotations)
    annotationProcessor(libs.bundles.annotations)
    // https://github.com/sundrio/sundrio/issues/104#issuecomment-460770907
    annotationProcessor(libs.kubernetes.client)

    api(libs.kubernetes.client)
    api(project(":packages:agones-crds"))
    api(project(":packages:common"))
    annotationProcessor(libs.kubernetes.crdsgen)
}
