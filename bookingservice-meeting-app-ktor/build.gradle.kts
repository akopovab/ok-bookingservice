import org.jetbrains.kotlin.util.suffixIfNot
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

val ktorVersion: String by project
val logbackVersion: String by project
val kotestVersion: String by project
val ktorKotestExtensionVersion: String by project
val datetimeVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    kotlin("jvm")
    id("application")
    id("com.bmuschko.docker-java-application")
    id("com.bmuschko.docker-remote-api")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
    mainClass.set("ru.otuskotlin.public.bookingservice.meeting.ApplicationKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor("core"))
    implementation(ktor("netty"))

    implementation(ktor("jackson", prefix = "serialization"))
    implementation(ktor("content-negotiation"))
    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors"))
    implementation(ktor("default-headers"))
    implementation(ktor("config-yaml"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")


    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.sndyuk:logback-more-appenders:1.8.8")
    implementation("org.fluentd:fluent-logger:0.3.4")


    implementation(project(":bookingservice-common"))
    implementation(project(":bookingservice-api"))
    implementation(project(":bookingservice-mappers"))
    implementation(project(":bookingservice-stubs"))
    implementation(project(":bookingservice-lib-logback"))
    implementation(project(":bookingservice-lib-log-common"))
    implementation(project(":bookingservice-mappers-log"))
    implementation(project(":bookingservice-api-log"))
    implementation(project(":bookingservice-business"))
    implementation(project(":bookingservice-repo-in-memory"))


    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-ktor:${ktorKotestExtensionVersion}")
    testImplementation(ktor("test-host"))
    testImplementation(ktor("content-negotiation", prefix = "client-"))
}

tasks {
    val dockerJvmDockerfile by creating(Dockerfile::class) {
        group = "docker"
        from("openjdk:17")
        copyFile("app.jar", "app.jar")
        entryPoint("java", "-Xms256m", "-Xmx512m", "-jar", "/app.jar")
    }
    create("dockerBuildJvmImage", DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerJvmDockerfile)
        doFirst {
            copy {
                from(dockerJvmDockerfile)
                into("${project.buildDir}/docker/app.jar")
            }
        }
        images.add("${project.name}:${project.version}")
    }

}

tasks.test {
    useJUnitPlatform()
}