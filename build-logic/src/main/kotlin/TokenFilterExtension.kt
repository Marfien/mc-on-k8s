import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.filter

fun Copy.applyStandardTokenFilter() {
    filter<ReplaceTokens>(
        "tokens" to mapOf(
            "project.version" to project.version.toString(),
            "project.name" to idToName(project.name),
            "project.group" to project.group.toString(),
            "project.id" to project.name,
            "project.description" to (project.description ?: "none")
        )
    )
}

fun idToName(name: String): String {
    val res = StringBuilder(name.length - 1)
    var nextUpperCase = true
    name.toCharArray().forEach {
        if (it == '-' || it == ' ') {
            nextUpperCase = true
            return@forEach
        }

        if (nextUpperCase) {
            res.append(it - 32)
            nextUpperCase = false
            return@forEach
        }

        res.append(it)
    }

    return res.toString()
}