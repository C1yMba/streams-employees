openapi: 3.0.3
info:
title: Streams Employees API
version: 1.0.0
description: REST API для управления сотрудниками и департаментами с системой рекомендаций

servers:
- url: http://localhost:8080
  description: Development server

paths:
/department/{id}/salary/sum:
get:
tags:
- Departments
summary: Получить сумму зарплат в департаменте
parameters:
- name: id
in: path
required: true
schema:
type: integer
description: ID департамента
responses:
'200':
description: Сумма зарплат
content:
application/json:
schema:
type: number
format: double
'400':
description: Департамент не указан

/department/{id}/salary/max:
get:
tags:
- Departments  
summary: Получить сотрудника с максимальной зарплатой в департаменте
parameters:
- name: id
in: path
required: true
schema:
type: integer
responses:
'200':
description: Сотрудник с максимальной зарплатой
content:
application/json:
schema:
$ref: '#/components/schemas/Employee'

/department/{id}/salary/min:
get:
tags:
- Departments
summary: Получить сотрудника с минимальной зарплатой в департаменте
parameters:
- name: id
in: path
required: true
schema:
type: integer
responses:
'200':
description: Сотрудник с минимальной зарплатой
content:
application/json:
schema:
$ref: '#/components/schemas/Employee'

/department/{id}/employees:
get:
tags:
- Departments
summary: Получить всех сотрудников департамента
parameters:
- name: id
in: path
required: true
schema:
type: integer
responses:
'200':
description: Список сотрудников департамента
content:
application/json:
schema:
type: array
items:
$ref: '#/components/schemas/Employee'

/department/employees:
get:
tags:
- Departments
summary: Получить всех сотрудников, сгруппированных по департаментам
responses:
'200':
description: Сотрудники по департаментам
content:
application/json:
schema:
type: object
additionalProperties:
type: array
items:
$ref: '#/components/schemas/Employee'

/api/recommendations/employee/{id}:
get:
tags:
- Recommendations
summary: Получить рекомендации для сотрудника
parameters:
- name: id
in: path
required: true
schema:
type: integer
description: ID сотрудника
responses:
'200':
description: Список рекомендаций
content:
application/json:
schema:
type: array
items:
$ref: '#/components/schemas/Recommendation'

/api/cache/clear:
post:
tags:
- Cache Management
summary: Очистить кеш системы
responses:
'200':
description: Кеш успешно очищен
content:
application/json:
schema:
type: string
example: "Cache cleared successfully"

components:
schemas:
Employee:
type: object
properties:
id:
type: integer
description: Уникальный идентификатор сотрудника
example: 1
fullName:
type: string
description: Полное имя сотрудника
example: "Иванов Иван Иванович"
department:
type: integer
description: ID департамента
example: 1
employeeSalary:
type: number
format: double
description: Зарплата сотрудника
example: 50000.0

    Recommendation:
      type: object
      properties:
        id:
          type: integer
          description: ID рекомендации
        employeeId:
          type: integer
          description: ID сотрудника
        type:
          type: string
          enum: [SALARY_INCREASE, PROMOTION, TRAINING]
          description: Тип рекомендации
        description:
          type: string
          description: Описание рекомендации
        priority:
          type: number
          format: double
          description: Приоритет рекомендации (0.0-1.0)
        createdAt:
          type: string
          format: date-time
          description: Время создания рекомендации