import com.google.protobuf.gradle.id

plugins {
    id("mc-on-k8s.java-conventions")
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
    protoc {
        artifact = libs.protoc.get().toString()
    }
    plugins {
        id("grpc") {
            artifact = libs.grpc.gen.get().toString()
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