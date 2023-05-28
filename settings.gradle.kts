rootProject.name = "ok-bookingservice"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}

//include("m1l1-quickstart")
//include("ok-bookingservice-test")
include("bookingservice-api")
include("bookingservice-common")
include("bookingservice-mappers")