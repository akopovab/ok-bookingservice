package ru.otuskotlin.public.bookingservice.lib.log.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class BsLoggerProvider(
    private val provider: (String) -> IBsLogWrapper = { IBsLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
    fun logger(function: KFunction<*>) = provider(function.name)

}
