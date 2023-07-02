package ru.otuskotlin.public.bookingservice.repo.postgresql.entities

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import java.util.UUID


object Meeting : Table("t_meeting") {
    val id = varchar("id", 36).uniqueIndex()
    val clientId = varchar("client_id", 36)
    val employeeId = varchar("employee_id", 36)
    val status = enumeration("status", BsMeetingStatus::class)
    val description = varchar("description", 1000)
    val lock = varchar("status", 36)

    override val primaryKey = PrimaryKey(MeetingSlot.id)

    fun from(res : ResultRow) = BsMeeting(
        id = BsMeetingId(res[id].toString()),
        clientId = BsClientId(res[clientId].toString()),
        employeeId = BsEmployeeId(res[employeeId].toString()),
        meetingStatus = res[status],
        description = res[description].toString(),
        lock = BsMeetingLock(res[lock].toString()),
    )

    fun from(res : InsertStatement<Number>) = BsMeeting(
        id = BsMeetingId(res[id].toString()),
        clientId = BsClientId(res[clientId].toString()),
        employeeId = BsEmployeeId(res[employeeId].toString()),
        meetingStatus = res[status],
        description = res[description].toString(),
        lock = BsMeetingLock(res[lock].toString()),
    )

    fun to(it : UpdateBuilder<*>, meeting :BsMeeting, uuidId: () -> String){
        it[id] = meeting.id.takeIf { it != BsMeetingId.NONE }?.asString() ?: uuidId()
        it[clientId] = meeting.clientId.asString()
        it[employeeId] = meeting.employeeId.asString()
        it[status] = meeting.meetingStatus
        it[description] = meeting.description
        it[lock] = meeting.lock.takeIf { it != BsMeetingLock.NONE }?.asString() ?: UUID.randomUUID().toString()
    }
}


