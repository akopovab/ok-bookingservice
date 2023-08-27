package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitReadObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoReadTest

class RepoPostgresSqlReadTest :FunSpec({
    include(
        repoReadTest(
            SqlTestCompanion.repoUnderTestContainer(
                initObjects = InitReadObjects.initObjects,
                initSlots = InitReadObjects.initSlots
            )
        )
    )
})