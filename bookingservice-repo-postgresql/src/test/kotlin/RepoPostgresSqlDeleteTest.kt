package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitDeleteObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoDeleteTest

class RepoPostgresSqlDeleteTest : FunSpec({
    include(
        repoDeleteTest(
            SqlTestCompanion.repoUnderTestContainer(
                initObjects = InitDeleteObjects.initObjects,
                initSlots = InitDeleteObjects.initSlots
            )
        )
    )
})