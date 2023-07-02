package ru.otuskotlin.public.bookingservice.business.processors.impl

import chains.repository
import ru.otuskotlin.public.bookingservice.business.chains.operation
import ru.otuskotlin.public.bookingservice.business.chains.validation
import ru.otuskotlin.public.bookingservice.business.workers.initStatus
import ru.otuskotlin.public.bookingservice.business.workers.repo.initRepo
import ru.otuskotlin.public.bookingservice.business.workers.repo.repoMeetingCreate
import ru.otuskotlin.public.bookingservice.business.workers.repo.repoMeetingPrepareCreate
import ru.otuskotlin.public.bookingservice.business.workers.repo.repoMeetingPrepareResult
import ru.otuskotlin.public.bookingservice.business.workers.stubs.stubSlotBadEmployeeId
import ru.otuskotlin.public.bookingservice.business.workers.stubs.stubSlotSearchSuccess
import ru.otuskotlin.public.bookingservice.business.workers.validations.validationSlotEmployeeId
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.chain
import workers.repo.repoMeetingPrepareResult
import workers.repo.repoSlotSearch


class SlotProcessor {
    suspend fun exec(ctx: BsSlotContext) = SLOT_CHAIN.exec(ctx)

    companion object {
        private val SLOT_CHAIN = chain<BsSlotContext> {
            initStatus("Установка стартового статуса")
            initRepo("Инициализация репозитория")
            operation("Создание встречи", BsSlotCommand.SEARCH) {
                stubSlotSearchSuccess("Успешный поиск слотов")
                stubSlotBadEmployeeId("Ошибка поиска слотов, некорректный id сотрудника")
            }
            validation("Валидация запроса на поиск слотов"){
                validationSlotEmployeeId("Проверка наличия id сотрудника")
            }
            repository("Логика поиска слотов"){
                repoSlotSearch("Поиск слотов")
                repoMeetingPrepareResult("Подготовка ответа")
            }
        }.build()
    }


}