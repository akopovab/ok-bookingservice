package ru.otuskotlin.public.bookingservice.repo.postgresql

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitSearchObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoSearchMeetingTest

class RepoPostgresSqlSearchMeetingTest : FunSpec({
    include(
        repoSearchMeetingTest(
            SqlTestCompanion.repoUnderTestContainer(
                initObjects = InitSearchObjects.initObjects,
                initSlots = InitSearchObjects.initSlots
            )
        )
    )
})