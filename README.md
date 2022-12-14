# job4j_cinema

## Описание проекта

Учебный проект для раздела Web курса Job4J: Middle

Данный проект представляет собой сервис покупки билетов в кинотеатр.

Пользователь может купить билет на сеанс только при условии, что выбранный ряд и место 
на выбранном сеансе еще не заняты другим пользователем.

## Стек технологий
- Java 17
- Spring Boot
- JDBC
- PostgreSQL
- Thymeleaf
- Boostrap
- Liquibase

## Требования к окружению
- Java 17+
- Maven 3.8
- PostgreSQL 14

## Настройка проекта

### Создание схемы БД
```CREATE DATABASE cinema```

### Инициализация БД
В командной строке перейти в папку проекта и запустить команду<br>
```mvn liquibase:update```

### Настройка подключения к БД
В папке проекта /src/main/resources создать файл db.properties, в котором указать параметры подкдючения к базе данных.<br>
В качестве примера можно использовать файл db.properties.example.<br>
Пример содержимого файла настроек db.properties:<br>
```
jdbc.url=jdbc:postgresql://hostname:port/db_name
jdbc.username=db_username
jdbc.password=db_password
jdbc.driver=org.postgresql.Driver
```

## Запуск приложения
```mvn spring-boot:run```


## Взаимодействие с приложением
### Авторизация пользователя
![Авторизация пользователя](/img/001_login_page.png)

### Выбор сеанса
![Выбор сеанса](/img/002_select_session_page.png)

### Выбор места в зале
![Выбор места в зале](/img/003_select_seat_page.png)

### Подтверждение заказа
![Подтверждение заказа](/img/004_review_order_page.png)


## Контакты
email: nikolai.gladkikh.biz22@gmail.com