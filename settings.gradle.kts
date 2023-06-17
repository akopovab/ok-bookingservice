rootProject.name = "ok-bookingservice"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorPluginVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("m1l1-quickstart")
//include("ok-bookingservice-test")
include("bookingservice-api")
include("bookingservice-common")
include("bookingservice-mappers")
include("bookingservice-meeting-app-ktor")
//include("bookingservice-slot-app-ktor")
include("bookingservice-stubs")
include("bookingservice-api-log")
include("bookingservice-mappers-log")
include("bookingservice-lib-log-common")
include("bookingservice-lib-logback")
include("bookingservice-lib-cor")
include("bookingservice-app-kafka")
