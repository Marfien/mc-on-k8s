import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.filter

fun Copy.applyStandardTokenFilter() {
    filter<ReplaceTokens>(
        "tokens" to mapOf(
            "project.version" to project.version.toString(),
            "project.name" to (project.displayName),
            "project.group" to project.group.toString(),
            "project.id" to project.name,
            "project.description" to (project.description ?: "none")
        )
    )
}