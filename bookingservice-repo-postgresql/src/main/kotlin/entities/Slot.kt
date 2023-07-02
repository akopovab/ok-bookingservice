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

object Slot : Table("t_slot") {
    val id = varchar("id", 36).uniqueIndex()
    private val startDate = datetime("start_date")
    private val endDate = datetime("end_date")
    private val status = enumeration("status", BsSlotStatus::class)
    private val employeeId = varchar("employee_id", 36)

    override val primaryKey = PrimaryKey(MeetingSlot.id)

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
        it[startDate] = LocalDateTime.ofInstant(slot.startDate.toJavaInstant(), ZoneId.systemDefault())
        it[endDate] = LocalDateTime.ofInstant(slot.endDate.toJavaInstant(), ZoneId.systemDefault())
        it[status] = slot.slotStatus
        it[employeeId] = slot.employeeId.asString()
    }




}

