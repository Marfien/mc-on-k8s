import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
import java.nio.charset.StandardCharsets

plugins {
    id("java")
    id("mc-on-k8s.general-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }

    test {
        useJUnitPlatform()
        reports {
            junitXml.required.set(true)
        }
    }
}

extensions.configure<SpotlessExtension> {
    java {
        target("src")
        removeUnusedImports()
        googleJavaFormat().reorderImports(true)
        formatAnnotations()
        encoding = StandardCharsets.UTF_8
        lineEndings = LineEnding.UNIX
    }
}