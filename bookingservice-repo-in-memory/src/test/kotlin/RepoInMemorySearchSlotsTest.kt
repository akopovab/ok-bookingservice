package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.kotest.core.spec.style.FunSpec
import ru.otuskotlin.public.bookingservice.repo.tests.repoSearchSlotsTest

class RepoInMemorySearchSlotsTest : FunSpec({
    include(
        repoSearchSlotsTest(MeetingRepoInMemory())
    )
})