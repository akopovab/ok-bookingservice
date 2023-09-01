package ru.otuskotlin.public.bookingservice.business.workers.access

import ru.otuskotlin.public.bookingservice.auth.resolveRelationTo
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker


fun <T : BsMeetingContext> CorChainDsl<T>.calcAccessPrincipalSearch(title: String) = worker {
    this.title = title
    handle {
        meetingRepoPrepare.principalRelation = meetingRepoPrepareSearch.resolveRelationTo(principal)
        println("-------------------->>>")
        println("principalRelation ctx: ${this.requestId}")
        println(meetingRepoPrepare.principalRelation)
        println("<<<--------------------")
        println("-------------------->>>")
        println("principal ctx ${this.requestId}")
        println(principal)
        println("<<<--------------------")
        println("-------------------->>>")
        println("meetingRepoPrepareSearch ${this.requestId}")
        println(meetingRepoPrepareSearch)
        println("<<<--------------------")
    }
    on { state == BsState.RUNNING }

}