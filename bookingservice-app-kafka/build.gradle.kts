plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.otuskotlin.public.bookingservice.app.kafka.MainKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("bookingserviceKafka")
//        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val kotestVersion: String by project


    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models
    implementation(project(":bookingservice-common"))
    implementation(project(":bookingservice-api"))
    implementation(project(":bookingservice-mappers"))
    implementation(project(":bookingservice-stubs"))
    implementation(project(":bookingservice-business"))
    // test
    //testImplementation(kotlin("test-junit"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

