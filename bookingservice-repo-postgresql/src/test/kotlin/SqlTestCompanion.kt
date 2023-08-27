package ru.otuskotlin.public.bookingservice.repo.postgresql

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import java.time.Duration
import org.testcontainers.containers.PostgreSQLContainer
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot


class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "bookingservice"
    private const val SCHEMA = "bookingservice"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: List<BsMeeting> = emptyList(),
        initSlots: Set<BsSlot> = emptySet(),
    ): MeetingRepoSql {
        return MeetingRepoSql(
            SqlProperties(url, USER, PASS, SCHEMA, dropTable = true),
            initObjects,
            initSlots
        )
    }
}