import com.diffplug.spotless.LineEnding
import java.nio.charset.StandardCharsets

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.spotless)
    implementation(libs.plugin.shadow)
}

spotless {
    kotlin {
        encoding = StandardCharsets.UTF_8
        lineEndings = LineEnding.UNIX
    }
}