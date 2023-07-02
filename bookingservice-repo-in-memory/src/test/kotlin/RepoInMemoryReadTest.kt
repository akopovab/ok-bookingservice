package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitCreateObjects
import ru.otuskotlin.public.bookingservice.repo.tests.InitReadObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoReadTest

class RepoInMemoryReadTest: FunSpec({
    include(
        repoReadTest(MeetingRepoInMemory(InitReadObjects.initObjects, InitCreateObjects.initSlots))
    )
})

