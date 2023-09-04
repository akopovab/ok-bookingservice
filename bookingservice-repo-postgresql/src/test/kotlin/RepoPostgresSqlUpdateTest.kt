package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitUpdateObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoUpdateTest

class RepoPostgresSqlUpdateTest :FunSpec({
    include(
        repoUpdateTest(
            SqlTestCompanion.repoUnderTestContainer(
                initObjects = InitUpdateObjects.initObjects,
                initSlots = InitUpdateObjects.initSlots
            )
        )
    )
})