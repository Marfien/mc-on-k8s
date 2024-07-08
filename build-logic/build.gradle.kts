plugins {
    `kotlin-dsl`
}

dependencies {
    // Workaround for accessing libs
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.shadow)
}
