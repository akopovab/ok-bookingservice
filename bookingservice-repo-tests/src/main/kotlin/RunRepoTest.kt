package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.FunSpecTestFactoryConfiguration
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext


@OptIn(ExperimentalCoroutinesApi::class)
fun FunSpecTestFactoryConfiguration.repoTest(
    description: String,
    block: suspend TestScope.() -> Unit
) = test(description) {
    runTest {
        withContext(Dispatchers.Default) {
            block()
        }
    }
}

//fun baseSuccessTest(result: DbMeetingResponse, expected: BsMeeting) {
//    result.isSuccess shouldBe true
//    result.errors shouldBe emptyList()
//    result.data?.id shouldNotBe BsMeetingId.NONE
//    result.data?.clientId shouldBe expected.clientId
//    result.data?.employeeId shouldBe expected.employeeId
//
//}
