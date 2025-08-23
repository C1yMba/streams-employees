# Streams Employees - Система управления сотрудниками

## Описание проекта
REST API для управления сотрудниками организации с возможностью получения статистики по департаментам, расчета зарплат и генерации рекомендаций для HR-менеджеров.

## Стек технологий
- **Backend**: Java 17, Spring Boot 3.1.5
- **Web**: Spring Web (REST API)
- **Documentation**: SpringDoc OpenAPI 3 (Swagger)
- **Testing**: Spring Boot Test, JUnit 5
- **Build**: Maven

## Основные возможности
- 📊 Управление сотрудниками и департаментами
- 💰 Расчет статистики по зарплатам
- 📈 Генерация рекомендаций для HR
- 📝 Автоматическая документация API
- ⚡ Кеширование для повышения производительности

## Навигация по документации
- [Требования к системе](Requirements.md)
- [Архитектура приложения](Architecture.md)
- [API документация](API.md)
- [Инструкция по развертыванию](Deployment.md)

## Быстрый старт
```bash
git clone https://github.com/C1yMba/streams-employees.git
cd streams-employees
./mvnw spring-boot:run