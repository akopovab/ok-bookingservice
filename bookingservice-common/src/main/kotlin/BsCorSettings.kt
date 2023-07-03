package ru.otuskotlin.public.bookingservice.common


import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.lib.log.common.BsLoggerProvider

data class BsCorSettings(
    val loggerProvider: BsLoggerProvider = BsLoggerProvider(),
    val repoSettings: Map<BsWorkMode, IMeetingRepository> = BsWorkMode.values()
        .associateWith { IMeetingRepository.NONE}
) {

    companion object {
        val NONE = BsCorSettings()
    }
}
