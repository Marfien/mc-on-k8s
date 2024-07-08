import gradle.kotlin.dsl.accessors._8c47cae829ea3d03260d5ff13fb2398e.processResources
import org.apache.tools.ant.filters.ReplaceTokens

tasks {
    processResources {
        val version: String by project

        filter<ReplaceTokens>("tokens" to mapOf(
            "project.version" to version
        ))
    }
}
