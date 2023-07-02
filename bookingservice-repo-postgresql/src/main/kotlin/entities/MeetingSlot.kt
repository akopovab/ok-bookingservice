package ru.otuskotlin.public.bookingservice.repo.postgresql.entities

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


object MeetingSlot : Table("t_meeting_slot") {
    val id = varchar("id", 36).uniqueIndex()
    val slotId = reference("slot_id", Slot.id, onDelete = ReferenceOption.CASCADE)
    val meetingId = reference("meeting_id", Meeting.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}