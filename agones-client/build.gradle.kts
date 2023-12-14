import com.google.protobuf.gradle.id

plugins {
    id("mc-on-k8s.java-publish-conventions")
    id("java-library")
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(libs.protobuf)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)
    implementation(libs.annotationsapi)
    implementation(libs.grpc.netty)
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