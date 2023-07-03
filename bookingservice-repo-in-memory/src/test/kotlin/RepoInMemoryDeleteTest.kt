package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitDeleteObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoDeleteTest

class RepoInMemoryDeleteTest : FunSpec({
    include(
        repoDeleteTest(MeetingRepoInMemory(InitDeleteObjects.initObjects, InitDeleteObjects.initSlots))
    )
})
