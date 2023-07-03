package ru.otuskotlin.public.bookingservice.repo.postgresql.entities

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Slot : Table("t_slot") {
    val id = varchar("id", 36).uniqueIndex()
    private val startDate = varchar("start_date", 36)
    private val endDate = varchar("end_date", 36)
    private val status = enumeration("status", BsSlotStatus::class)
    private val employeeId = varchar("employee_id", 36)

    override val primaryKey = PrimaryKey(id)

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    fun from(res : ResultRow) = BsSlot(
        id = BsSlotId(res[id].toString()),
        startDate = Instant.parse(res[startDate].toString()),
        endDate = Instant.parse(res[endDate].toString()),
        slotStatus = res[status],
        employeeId = BsEmployeeId(res[employeeId].toString())
    )

    fun from(res : InsertStatement<Number>) = BsSlot(
        id = BsSlotId(res[id].toString()),
        startDate = Instant.parse(res[startDate].toString()),
        endDate = Instant.parse(res[endDate].toString()),
        slotStatus = res[status],
        employeeId = BsEmployeeId(res[employeeId].toString())
    )


    fun to(it: UpdateBuilder<*>, slot : BsSlot, uuidId: () -> String) {
        it[id] = slot.id.takeIf { it != BsSlotId.NONE }?.asString() ?: uuidId()
        it[startDate] = slot.startDate.toString()
        it[endDate] = slot.endDate.toString()
        it[status] = slot.slotStatus
        it[employeeId] = slot.employeeId.asString()
    }




}

