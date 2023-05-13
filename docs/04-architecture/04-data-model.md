## Модель данных

### Запись на прием (MeetRequest):
- id - индентификатор сущности
- slotId - идентификатор слота
- personId - id сотрудника
- firstName - имя заявителя
- secondName - отчество заявителя
- lastName - фио заявителя
- phoneNumber - телефон заявителя
- email - электронная почта заявителя
- meetRequestStatus - статус встречи

### Слот (Slot):
- id - индентификатор сущности
- startDate - дата и время начала встречи
- endDate - дата и время завершения встречи
- personId - индентификатор сотрудника
- slotStatus - статус слота

### Пользователь (Person):
- id - индентификатор сущности
- firstName - имя пользователя
- secondName - отчество пользователя
- lastName - фио пользователя
- workPhone - рабочий телефон
- cabinetNum - кабинет
- orgPosition - должность

### Статус встречи (MeetRequest) Enum
- CREATE - создано
- CONFIRMED - встреча проведена
- MEET_UNDONE - встреча не состоялась
- WITHDRAWN - отозвано

### Статус слота (SlotStatus) Enum
- RESERVED - зарезервировано
- FREE - свободно