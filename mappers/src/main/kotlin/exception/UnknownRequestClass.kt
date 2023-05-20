package ru.otuskotlin.public.bookingservice.mappers.exception

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to MkplContext")
