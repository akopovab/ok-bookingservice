package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitCreateObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoCreateTest

class RepoInMemoryCreateTest :FunSpec ({
    include(
        repoCreateTest(MeetingRepoInMemory(InitCreateObjects.initObjects, InitCreateObjects.initSlots))
    )
})