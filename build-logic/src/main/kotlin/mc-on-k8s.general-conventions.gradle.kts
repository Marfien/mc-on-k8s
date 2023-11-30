import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import gradle.kotlin.dsl.accessors._7758f57c6dd35933dbd9dc8c103ba8e9.processResources

apply<SpotlessPlugin>()

extensions.configure<SpotlessExtension> {

}

tasks {
    processResources {
        applyStandardTokenFilter()
    }
}