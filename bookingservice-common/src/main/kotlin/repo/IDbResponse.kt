package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.BsError

interface IDbResponse<T> {
    val data :T?
    val isSuccess: Boolean
    val errors :List<BsError>
}