package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.postgresql.SqlTestCompanion.repoUnderTestContainer
import ru.otuskotlin.public.bookingservice.repo.tests.InitCreateObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoCreateTest

class RepoPostgresSqlCreateTest : FunSpec({
    include(
        repoCreateTest(
            repoUnderTestContainer(
                initObjects = InitCreateObjects.initObjects,
                initSlots = InitCreateObjects.initSlots
            )
        )
    )
})
