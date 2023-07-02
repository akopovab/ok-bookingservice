package ru.otuskotlin.public.bookingservice.meeting.plugins

import io.ktor.server.application.*
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.repo.inmemory.MeetingRepoInMemory

object ConfigPaths {
    private const val BsRoot = "bookingservice"
    const val repository = "$BsRoot.repository"
}

fun Application.getRepository() =
    BsWorkMode.values().associateWith {
        it.takeIf { it != BsWorkMode.STUB }?.let { mode ->
            when (environment.config.propertyOrNull(ConfigPaths.repository + "." + mode.name.lowercase())?.getString()
                !!.lowercase()) {
                "in-memory", "inmemory", "memory", "mem" -> MeetingRepoInMemory()
                "postgres", "postgresql", "pg", "sql", "psql" -> IMeetingRepository.NONE
                else -> throw IllegalArgumentException(
                    "$ConfigPaths must be set in application.yml to one of: 'in-memory', 'psql'"
                )
            }

        } ?: IMeetingRepository.NONE
    }





