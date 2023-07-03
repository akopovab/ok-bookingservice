package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.InitUpdateObjects
import ru.otuskotlin.public.bookingservice.repo.tests.repoUpdateTest

class RepoInMemoryUpdateTest : FunSpec({
    include(
        repoUpdateTest(MeetingRepoInMemory(InitUpdateObjects.initObjects, InitUpdateObjects.initSlots))
    )
})