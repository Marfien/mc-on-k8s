plugins {
    id("java")
}

tasks {
    val tokenTask = create<Copy>("replaceTokens") {
        from("src/main")
        into("$buildDir/generated/tokens")
    }

    compileJava {
        dependsOn(tokenTask)
        setSource(tokenTask.destinationDir)
    }
}