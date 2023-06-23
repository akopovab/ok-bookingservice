package ru.otuskotlin.public.bookingservice.lib.logback


import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import kotlin.reflect.KClass

fun mpLoggerLogback(logger: Logger): IBsLogWrapper = BsLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerLogback(clazz: KClass<*>): IBsLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

fun mpLoggerLogback(loggerId: String): IBsLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
