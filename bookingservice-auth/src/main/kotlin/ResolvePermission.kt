package ru.otuskotlin.public.bookingservice.auth

import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserGroup
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserPermission

fun resolvePermissions(
    groups: Iterable<BsUserGroup>
) = mutableSetOf<BsUserPermission>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    BsUserGroup.USER to setOf(
        BsUserPermission.UPDATE,
        BsUserPermission.CREATE,
        BsUserPermission.READ
    ),
    BsUserGroup.EMPLOYEE to setOf(
        BsUserPermission.UPDATE,
        BsUserPermission.CREATE,
        BsUserPermission.READ
    ),
    BsUserGroup.ADMIN to setOf(
        BsUserPermission.UPDATE,
        BsUserPermission.CREATE,
        BsUserPermission.READ,
        BsUserPermission.DELETE
    ),
    BsUserGroup.BANNED to setOf(BsUserPermission.READ),
    BsUserGroup.TEST to emptySet()
)

private val groupPermissionsDenys = mapOf(
    BsUserGroup.USER to setOf(BsUserPermission.DELETE),
    BsUserGroup.EMPLOYEE to setOf(BsUserPermission.DELETE),
    BsUserGroup.ADMIN to emptySet(),
    BsUserGroup.BANNED to setOf(
        BsUserPermission.CREATE,
        BsUserPermission.DELETE,
        BsUserPermission.UPDATE
    ),
    BsUserGroup.TEST to emptySet()
)
