package ru.otuskotlin.public.bookingservice.auth

import ru.otuskotlin.public.bookingservice.common.models.BsCommand
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipalRelations
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserPermission


fun checkPermitted(
    command: BsCommand,
    relation: Iterable<BsPrincipalRelations>,
    permissions: Iterable<BsUserPermission>,
) = relation.asSequence().flatMap { relation ->
    permissions.map { permission ->
        AccessTableConditions(
            command = command,
            permission = permission,
            relation = relation,
        )
    }
}.any {
    accessTable[it] != null
}

private data class AccessTableConditions(
    val command: BsCommand,
    val permission: BsUserPermission,
    val relation: BsPrincipalRelations
)

private val accessTable = mapOf(
    AccessTableConditions(
        command = BsMeetingCommand.CREATE,
        permission = BsUserPermission.CREATE,
        relation = BsPrincipalRelations.NEW
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.CREATE,
        permission = BsUserPermission.CREATE,
        relation = BsPrincipalRelations.OWN_CLIENT
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.UPDATE,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.OWN_CLIENT
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.UPDATE,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.OWN_EMPLOYEE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.UPDATE,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.MODERATABLE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.READ,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.OWN_CLIENT
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.READ,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.OWN_EMPLOYEE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.READ,
        permission = BsUserPermission.UPDATE,
        relation = BsPrincipalRelations.MODERATABLE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.DELETE,
        permission = BsUserPermission.DELETE,
        relation = BsPrincipalRelations.MODERATABLE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.SEARCH,
        permission = BsUserPermission.READ,
        relation = BsPrincipalRelations.MODERATABLE
    ) to true,
    AccessTableConditions(
        command = BsMeetingCommand.SEARCH,
        permission = BsUserPermission.READ,
        relation = BsPrincipalRelations.OWN_EMPLOYEE
    ) to true
)