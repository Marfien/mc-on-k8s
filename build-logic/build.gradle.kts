import com.diffplug.spotless.LineEnding
import java.nio.charset.StandardCharsets

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.plugins.spotless.get().version}")
}

spotless {
    kotlin {
        encoding = StandardCharsets.UTF_8
        lineEndings = LineEnding.UNIX
    }
}