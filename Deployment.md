# Инструкция по развертыванию Streams Employees

## Системные требования

### Минимальные требования
- **Java**: 17 или выше
- **RAM**: 512 MB
- **Disk**: 100 MB свободного места
- **OS**: Windows 10+, Linux, macOS

### Рекомендуемые требования
- **Java**: 17 или выше
- **RAM**: 1 GB
- **Disk**: 500 MB свободного места

## Переменные окружения

### Обязательные переменные
```bash
# Порт приложения (по умолчанию 8080)
SERVER_PORT=8080

# Уровень логирования (по умолчанию INFO)
LOGGING_LEVEL_ROOT=INFO
```

### Опциональные переменные
```bash
# Настройки кеширования
SPRING_CACHE_TYPE=simple

# Настройки управления приложением
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info,cache

# Настройки Swagger
SPRINGDOC_API_DOCS_PATH=/api-docs
SPRINGDOC_SWAGGER_UI_PATH=/swagger-ui.html
```

## Сборка приложения
### Предварительные требования
Убедитесь, что установлены:

- **Java** 17+
- **Maven** 3.6+ (или используйте встроенный Maven Wrapper)

## Клонирование репозитория
```bash
git clone https://github.com/C1yMba/streams-employees.git
cd streams-employees
```

## Сборка JAR-файла

```bash
# Сборка без запуска тестов (быстрый вариант)
./mvnw clean package -DskipTests

# Сборка с запуском всех тестов (рекомендуется)
./mvnw clean package

# Очистка предыдущих сборок
./mvnw clean
```

После успешной сборки JAR-файл будет находиться в папке target/streams-hw-1.0-SNAPSHOT.jar

## Запуск приложения
### Локальный запуск
#### Запуск с настройками по умолчанию
```bash
java -jar target/streams-hw-1.0-SNAPSHOT.jar

# Запуск с кастомным портом
java -jar -Dserver.port=9090 target/streams-hw-1.0-SNAPSHOT.jar
```

#### Запуск с переменными окружения
```bash
SERVER_PORT=9090 java -jar target/streams-hw-1.0-SNAPSHOT.jar
Запуск в development режиме
bash# Через Maven (удобно для разработки)
./mvnw spring-boot:run
```

#### С кастомными настройками
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=9090
```
#### Production запуск
```bash
bash# Создание systemd service файла
sudo tee /etc/systemd/system/streams-employees.service > /dev/null <<EOF
[Unit]
Description=Streams Employees Service
After=network.target

[Service]
Type=simple
User=streams-app
ExecStart=/usr/bin/java -jar /opt/streams-employees/streams-hw-1.0-SNAPSHOT.jar
Environment=SERVER_PORT=8080
Environment=LOGGING_LEVEL_ROOT=WARN
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# Активация и запуск сервиса

sudo systemctl daemon-reload
sudo systemctl enable streams-employees
sudo systemctl start streams-employees
```
