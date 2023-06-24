plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val coroutinesVersion: String by project
    val kotestVersion: String by project

    testImplementation(kotlin("test-junit"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

    implementation(project(":bookingservice-lib-cor"))
    implementation(project(":bookingservice-common"))
//    implementation(project(":bookingservice-api"))
//    implementation(project(":bookingservice-mappers"))
    implementation(project(":bookingservice-stubs"))
//    implementation(project(":bookingservice-lib-logback"))
//    implementation(project(":bookingservice-lib-log-common"))
//    implementation(project(":bookingservice-mappers-log"))
//    implementation(project(":bookingservice-api-log"))


}

tasks.test {
    useJUnitPlatform()
}