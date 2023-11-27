import com.google.protobuf.gradle.id

plugins {
    id("java")
    id("java-library")
    alias(libs.plugins.protobuf)
}

dependencies {
    compileOnlyApi(libs.protobuf)
    compileOnlyApi(libs.grpc.protobuf)
    compileOnlyApi(libs.grpc.stub)
    compileOnlyApi(libs.annotationsapi)
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/source/proto/main/java")
        }
        proto {
            srcDir("src/main/proto")
        }
    }
}

protobuf {
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.get()}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") {
                    outputSubDir = "java"
                }
            }
        }
    }
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}