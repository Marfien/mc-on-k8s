rootProject.name = "minecraft-on-k8s"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

fun isSubProject(file: File): Boolean {
    if (!file.isDirectory) return false

    val files = file.list()

    return files != null
            && files.contains("build.gradle.kts")
            && !files.contains("settings.gradle.kts")
}

rootDir.listFiles { it: File -> isSubProject(it)}?.forEach { include(it.name) }
