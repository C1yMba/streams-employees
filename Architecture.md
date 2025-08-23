# Архитектура приложения Streams Employees

## Use Case Diagramm
```mermaid
flowchart LR
    subgraph "Система Streams Employees"
        UC1[Просмотр статистики<br/>по департаменту]
        UC2[Управление списком<br/>сотрудников]
        UC3[Получение<br/>рекомендаций]
        UC4[Управление кешем<br/>системы]
        UC5[Мониторинг состояния<br/>приложения]
    end
    
    HR[👤 HR-менеджер] --> UC1
    HR --> UC2
    HR --> UC3
    
    Admin[👤 Системный<br/>администратор] --> UC4
    Admin --> UC5
    
    ExtSystem[🖥️ Внешняя система<br/>мониторинга] --> UC5
    ExtSystem --> UC4
    
    %% Styling
    classDef actor fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px
    classDef usecase fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    
    class HR,Admin,ExtSystem actor
    class UC1,UC2,UC3,UC4,UC5 usecase
```

## Диаграмма последовательности 

```mermaid
sequenceDiagram
    participant Client as HR Manager
    participant Controller as DepartmentsController
    participant Service as DepartmentService
    participant EmployeeService as EmployeeBookService
    participant Cache as Spring Cache
    participant Memory as In-Memory Storage
    
    Client->>+Controller: GET /department/1/salary/max
    Controller->>+Service: getDepartmentMaxSalary(1)
    
    Service->>+Cache: check cache for dept_1_max_salary
    Cache-->>-Service: cache miss
    
    Service->>+EmployeeService: getEmployees()
    EmployeeService->>+Memory: retrieve employee list
    Memory-->>-EmployeeService: List<Employee>
    EmployeeService-->>-Service: List<Employee>
    
    Service->>Service: filter by department & find max salary
    
    Service->>+Cache: store result in cache
    Cache-->>-Service: cached
    
    Service-->>-Controller: Employee with max salary
    Controller-->>-Client: HTTP 200 + Employee JSON
    
    Note over Client,Memory: Последующие запросы будут использовать кеш
    
    Client->>+Controller: GET /department/1/salary/max (повторный)
    Controller->>+Service: getDepartmentMaxSalary(1)
    Service->>+Cache: check cache for dept_1_max_salary
    Cache-->>-Service: cache hit - return cached result
    Service-->>-Controller: Employee with max salary (from cache)
    Controller-->>-Client: HTTP 200 + Employee JSON
```

## Диаграмма компонентов
```mermaid
graph TB
    subgraph "External Actors"
        HR[HR Manager<br/>HTTP Client]
        Admin[System Admin<br/>Admin Client]
        Monitor[Health Check<br/>Monitoring System]
    end
    
    subgraph "Spring Boot Application"
        subgraph "REST Controllers Layer"
            DC[DepartmentsController<br/>/department/**]
            RC[RecommendationController<br/>/api/recommendations/**]
            CC[CacheController<br/>/api/cache/**]
        end
        
        subgraph "Service Layer"
            DS[DepartmentService<br/>Business Logic]
            RS[RecommendationService<br/>Rules Engine]
            EBS[EmployeeBookService<br/>Core Operations]
        end
        
        subgraph "Data Layer"
            Memory[(In-Memory Storage<br/>Employee Data)]
            Cache[(Spring Cache<br/>Performance)]
            Static[(Static Rules<br/>Hardcoded)]
        end
    end
    
    %% External connections
    HR --> DC
    HR --> RC
    Admin --> CC
    Monitor --> CC
    
    %% Internal connections
    DC --> DS
    RC --> RS
    CC --> Cache
    
    DS --> EBS
    RS --> EBS
    
    EBS --> Memory
    RS --> Cache
    RS --> Static
    
    %% Styling
    classDef controller fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef service fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef data fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef external fill:#fff3e0,stroke:#e65100,stroke-width:2px
    
    class DC,RC,CC controller
    class DS,RS,EBS service
    class Memory,Cache,Static data
    class HR,Admin,Monitor external
```
## Диаграмма деятельности - Генерация рекомендаций
```mermaid
flowchart TD
    Start([Запрос рекомендаций для сотрудника]) --> GetEmployee[Получить данные сотрудника по ID]
    GetEmployee --> CheckCache{Проверить кеш рекомендаций}
    
    CheckCache -->|Найдено| ReturnCached[Вернуть кешированные рекомендации]
    ReturnCached --> End([Конец])
    
    CheckCache -->|Не найдено| InitRecommendations[Инициализировать список рекомендаций]
    InitRecommendations --> Rule1[Статическое правило 1:<br/>Проверка зарплаты относительно средней]
    
    Rule1 --> CheckSalaryLow{Зарплата < средней по департаменту - 20%?}
    CheckSalaryLow -->|Да| AddSalaryRec[Добавить рекомендацию:<br/>Повышение зарплаты]
    CheckSalaryLow -->|Нет| Rule2[Статическое правило 2:<br/>Проверка для обучения]
    AddSalaryRec --> Rule2
    
    Rule2 --> CheckMedian{Зарплата < медианной по департаменту?}
    CheckMedian -->|Да| AddTrainingRec[Добавить рекомендацию:<br/>Дополнительное обучение]
    CheckMedian -->|Нет| Rule3[Статическое правило 3:<br/>Проверка для продвижения]
    AddTrainingRec --> Rule3
    
    Rule3 --> CheckTop{Зарплата в топ-10% департамента?}
    CheckTop -->|Да| AddPromotionRec[Добавить рекомендацию:<br/>Продвижение по службе]
    CheckTop -->|Нет| SaveCache[Сохранить результат в кеш]
    AddPromotionRec --> SaveCache
    
    SaveCache --> ReturnRecommendations[Вернуть список рекомендаций]
    ReturnRecommendations --> End
    
    %% Styling
    classDef startEnd fill:#c8e6c9,stroke:#2e7d32,stroke-width:3px
    classDef process fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    classDef decision fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef action fill:#fce4ec,stroke:#c2185b,stroke-width:2px
    
    class Start,End startEnd
    class GetEmployee,InitRecommendations,Rule1,Rule2,Rule3,SaveCache,ReturnRecommendations,ReturnCached process
    class CheckCache,CheckSalaryLow,CheckMedian,CheckTop decision
    class AddSalaryRec,AddTrainingRec,AddPromotionRec action
```

## Описание компонентов

### REST Controllers
- **DepartmentsController**: Обрабатывает запросы для работы с департаментами и статистикой
- **RecommendationController**: Генерирует и возвращает рекомендации для сотрудников
- **CacheController**: Управление кешем системы

### Service Layer
- **EmployeeBookServiceImpl**: Основная бизнес-логика работы с сотрудниками
- **DepartmentServiceImpl**: Специализированная логика работы с департаментами
- **RecommendationService**: Движок генерации рекомендаций на основе правил

### Data Layer
- **In-Memory Storage**: Хранение данных сотрудников в памяти приложения
- **Spring Cache**: Кеширование результатов для повышения производительности

## Принципы архитектуры

1. **Layered Architecture** - четкое разделение на слои контроллеров, сервисов и данных
2. **Dependency Injection** - использование Spring DI для управления зависимостями
3. **RESTful API** - следование принципам REST для API endpoints
4. **Caching Strategy** - кеширование для оптимизации производительности
5. **Exception Handling** - централизованная обработка исключений

