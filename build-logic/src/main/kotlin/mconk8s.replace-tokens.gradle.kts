import org.apache.tools.ant.filters.ReplaceTokens

tasks.withType<ProcessResources> {
    val version: String by project

    filter<ReplaceTokens>("tokens" to mapOf(
            "project.version" to version
    ))
}
