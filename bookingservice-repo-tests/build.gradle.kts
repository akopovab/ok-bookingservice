plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {

    val kotestVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":bookingservice-common"))
    implementation(project(":bookingservice-stubs"))
    //implementation(project(":bookingservice-repo-in-memory"))
    implementation(project(":bookingservice-stubs"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    //implementation(kotlin("test-junit5"))
    implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")



}

tasks.test {
    useJUnitPlatform()
}