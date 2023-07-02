plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val cache4kVersion: String by project
    val coroutinesVersion: String by project
    val kotestVersion: String by project

    implementation(project(":bookingservice-common"))
    implementation(project(":bookingservice-stubs"))

    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    //implementation(project(":ok-bookingservice-repo-tests"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")


}

tasks.test {
    useJUnitPlatform()
}