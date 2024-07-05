plugins {
    id("java-library")
    alias(libs.plugins.jandex)
}

dependencies {
    compileOnly(libs.bundles.annotations)
    annotationProcessor(libs.bundles.annotations)
    // https://github.com/sundrio/sundrio/issues/104#issuecomment-460770907
    annotationProcessor(libs.kubernetes.client)

    api(libs.kubernetes.client)
}

java {
    toolchain {
        val javaVersion: String by project
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
