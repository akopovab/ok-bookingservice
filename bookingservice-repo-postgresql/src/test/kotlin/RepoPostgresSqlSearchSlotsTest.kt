package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.repoSearchSlotsTest

class RepoPostgresSqlSearchSlotsTest : FunSpec({
    include(
        repoSearchSlotsTest(
            SqlTestCompanion.repoUnderTestContainer()
        )
    )
})