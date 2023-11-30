plugins {
    id("mc-on-k8s.java-conventions")
    id("java-library")
}

dependencies {
    api(project(":gameserver-base-api"))
}