package ru.otuskotlin.public.bookingservice.lib.cor

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.Test
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.lib.cor.handle.CorChain
import ru.otuskotlin.public.bookingservice.lib.cor.handle.CorWorker

enum class TestStatus { NONE, RUNNING, ERROR }

data class TestContext(
    var workName: String = "",
    var count: Int = 0,
    var workStatus: TestStatus = TestStatus.NONE,
    var nullableString: String? = null
)

//@Test
class CorTest : FunSpec({

    test("base test cor") {
        val ctx = TestContext("Base test: ")
        val handle: CorWorker<TestContext> = CorWorker(
            title = "test 1",
            blockHandle = { workName += "result of one handle" }
        )
        handle.exec(ctx)
        ctx.workName shouldBe "Base test: result of one handle"
    }

    test("worker off") {
        val ctx = TestContext("Base test: worker off")
        val handle: CorWorker<TestContext> = CorWorker(
            title = "test 2",
            blockOn = { workStatus == TestStatus.RUNNING },
            blockHandle = { workName += "result of one handle" }
        )
        handle.exec(ctx)
        ctx.workName shouldBe "Base test: worker off"
    }

    test("chain handler test") {
        val ctx = TestContext("Chain test: ")
        val generateCtx: (String) -> CorWorker<TestContext> =
            { str -> CorWorker<TestContext>(blockHandle = { workName += str }, title = "multiHandle") }
        val handle: CorChain<TestContext> = CorChain(
            title = "test 3",
            handles = listOf(generateCtx("link one, "), generateCtx("link two"))
        )
        handle.exec(ctx)
        ctx.workName shouldBe "Chain test: link one, link two"
    }

    test("exception handler") {
        val ctx = TestContext(workName = "exception test: ")
        val handle: CorWorker<TestContext> = CorWorker(
            title = "base test",
            blockHandle = {count += nullableString!!.length},
            blockException = {if (it::class == NullPointerException()::class) workName += "NPE exception" }
        )
        handle.exec(ctx)
        ctx.workName shouldBe "exception test: NPE exception"
    }



})










