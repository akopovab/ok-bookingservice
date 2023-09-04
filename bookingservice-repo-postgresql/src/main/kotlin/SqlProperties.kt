package ru.otuskotlin.public.bookingservice.repo.postgresql

data class SqlProperties(
    val url: String = "jdbc:postgresql://psql:5432/bookingservice",
    val user: String = "postgres",
    val password: String = "bookingservice",
    val schema: String = "bookingservice",
    val dropTable: Boolean = true,
)