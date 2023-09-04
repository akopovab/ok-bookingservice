package ru.otuskotlin.public.bookingservice.meeting

import io.kotest.core.spec.style.FunSpec
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.assertEquals

class AuthTest : FunSpec({

    test("auth test") {
        testApplication {
            val client = createClient {}
            val response = client.post("/api/meeting/create") {
                contentType(ContentType.Application.Json)
            }
            assertEquals(401, response.status.value)
        }
    }
})