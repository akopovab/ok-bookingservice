package ru.otuskotlin.public.bookingservice.business.processors.impl

import chains.repository
import ru.otuskotlin.public.bookingservice.business.chains.operation
import ru.otuskotlin.public.bookingservice.business.chains.stubs
import ru.otuskotlin.public.bookingservice.business.chains.validation
import ru.otuskotlin.public.bookingservice.business.workers.initStatus
import ru.otuskotlin.public.bookingservice.business.workers.repo.*
import ru.otuskotlin.public.bookingservice.business.workers.stubs.*
import ru.otuskotlin.public.bookingservice.business.workers.validations.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.chain
import workers.repo.repoMeetingDelete
import workers.repo.repoMeetingSearch
import workers.repo.repoMeetingUpdate

class MeetingProcessor {

    suspend fun exec(ctx: BsMeetingContext) = MEETING_CHAIN.exec(ctx)

    companion object {
        private val MEETING_CHAIN = chain{
            initStatus("Установка стартового статуса")
            initRepo("Инициализация репозитория")
            operation("Создание встречи", BsMeetingCommand.CREATE) {
                stubs("Цепочка стабов") {
                    stubMeetingCreateSuccess("Успешное создание встречи")
                    stubMeetingBadClientId("Ошибка создания встречи, некорректный id клиента")
                    stubMeetingBadSlotId("Ошибка создания встречи, некорректный id слота")
                    stubMeetingBadEmployeeId("Ошибка создания встречи, некорректный id сотрудника")
                    stubMeetingBadDescription("Ошибка создания встречи, некорректное примечание к встрече")
                    stubMeetingSlotIdReserved("Ошибка создания встречи, слот уже был зарезервирован")
                    stubNoCase("Заданный стаб не существует")
                }
                validation("Валидация запроса на создание встречи"){
                    prepareMeetingValidation("Подготовка данных для валидации встречи")
                    validationMeetingClientId("Проверка наличия id клиента")
                    validationMeetingEmployeeId("Проверка наличия id сотрудника")
                    validationMeetingDescription("Проверка примечания к встрече")
                    validationMeetingSlot("Проверка слотов")
                }
                repository("Логика создания встречи"){
                    repoMeetingPrepareCreate("Подготовка создания встречи")
                    repoMeetingCreate("Создание встречи")
                    repoMeetingPrepareResult("Подготовка ответа")
                }
            }
            operation("Чтение встречи", BsMeetingCommand.READ) {
                stubs("Цепочка стабов") {
                    stubMeetingReadSuccess("Успешное чтение встречи")
                    stubMeetingBadId("Ошибка чтения встречи, некорректный id встречи")
                    stubNoCase("Заданный стаб не существует")
                }
                validation("Валидация запроса на чтение встречи"){
                    prepareMeetingValidation("Подготовка данных для валидации встречи")
                    validationMeetingId("Проверка наличия id встречи")
                }
                repository("Логика чтения встречи"){
                    repoMeetingRead("Чтение встречи")
                    repoMeetingPrepareResult("Подготовка ответа")
                }
            }
            operation("Изменение встречи", BsMeetingCommand.UPDATE) {
                stubs("Цепочка стабов") {
                    stubMeetingUpdateSuccess("Успешное изменение встречи")
                    stubMeetingBadId("Ошибка изменения встречи, некорректный id встречи")
                    stubMeetingBadClientId("Ошибка изменения встречи, некорректный id клиента")
                    stubMeetingBadSlotId("Ошибка изменения встречи, некорректный id слота")
                    stubMeetingBadEmployeeId("Ошибка изменения встречи, некорректный id сотрудника")
                    stubMeetingBadDescription("Ошибка изменения встречи, некорректное примечание к встрече")
                    stubMeetingSlotIdReserved("Ошибка создания встречи, слот уже был зарезервирован")
                    stubNoCase("Заданный стаб не существует.")
                }
                validation("Валидация запроса на изменение встречи"){
                    prepareMeetingValidation("Подготовка данных для валидации встречи")
                    validationMeetingId("Проверка наличия id встречи")
                    validationMeetingClientId("Проверка наличия id клиента")
                    validationMeetingEmployeeId("Проверка наличия id сотрудника")
                    validationMeetingDescription("Проверка примечания к встрече")
                    validationMeetingSlot("Проверка слотов")
                }
                repository("Логика чтения встречи"){
                    repoMeetingUpdate("Логика обновления")
                    repoMeetingPrepareResult("Подготовка ответа")
                }
            }
            operation("Удаление встречи", BsMeetingCommand.DELETE) {
                stubs("Цепочка стабов") {
                    stubMeetingDeleteSuccess("Успешное удаление встречи")
                    stubMeetingBadId("Ошибка удаления встречи, некорректный id встречи")
                    stubNoCase("Заданный стаб не существует")
                }
                validation("Валидация запроса на удаление встречи"){
                    prepareMeetingValidation("Подготовка данных для валидации встречи")
                    validationMeetingId("Проверка наличия id встречи")
                }
                repository("Логика чтения встречи"){
                    repoMeetingDelete("Логика удаления")
                    repoMeetingPrepareResult("Подготовка ответа")
                }
            }
            operation("Поиск встреч", BsMeetingCommand.SEARCH) {
                stubs("Цепочка стабов") {
                    stubMeetingSearchSuccess("Успешный поиск встреч")
                    stubMeetingBadEmployeeId("Ошибка поиска встреч, некорректный id сотрудника")
                    stubNoCase("Заданный стаб не существует")
                }
                validation("Валидация запроса на поиск встречи"){
                    prepareMeetingValidation("Подготовка данных для валидации встречи")
                    validationEmployeeId("Проверка наличия id сотрудника")
                }
                repository("Логика поиска встречи"){
                    repoMeetingSearch("Поиск встречи")
                    repoMeetingPrepareResult("Подготовка ответа")
                }
            }
        }.build()
    }

}
