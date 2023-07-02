package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitSearchObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoSearchMeetingTest

class RepoInMemorySearchMeetingTest : FunSpec({
    include(
        repoSearchMeetingTest(MeetingRepoInMemory(InitSearchObjects.initObjects, InitSearchObjects.initSlots))
    )
})