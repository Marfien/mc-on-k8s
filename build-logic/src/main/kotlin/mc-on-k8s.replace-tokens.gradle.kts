import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.create

plugins {
    id("java") apply false
}

tasks {
    val tokenTask = create<Copy>("replaceTokens") {
        from("src/main")
        into("$buildDir/generated/tokens")
        applyStandardTokenFilter()
    }

    compileJava {
        dependsOn(tokenTask)
        setSource(tokenTask.destinationDir)
    }
}

