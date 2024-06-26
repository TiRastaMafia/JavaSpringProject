# Приложение для управления заказами и клиентской базой

> Приложение предназначено для использования в сфере обращения товаров и услуг. В настоящий момент приложение представлено 
в виде серверной части с предоставлением API для взаимодействия клиентской части приложения, которое может быть реализовано
в любом удобном для конечного пользователя формате. 


## Основные цели и задачи:

- Основной целью является создание такого решения, которое будет доступно для любого, даже самого мелкого бизнеса, занятого 
в сфере обращения товаров и услуг.
- Основной задачей  было выделить основной функционал, который может быть применен в любом направлении
- Приложение должно быть оегко применено на практике и доступно для расширения функционала под потребности заказчика

## Проблема:

- Приложение такие проблемы, как:
  - Дорогостоящее внедрение существующего или разработка нового решения для решения основных задач бизнеса
  - Короткий срок внедрения и настройки для начала работы
  - Быстрое расширение функционала под индивидуальные потребности конкретного бизнеса

## Технологии:

- Java
- Spring
- Spring Security
- AOP AssertJ
- ORM Hibernate
- PostgreSQL
- Html
- RESTful API
- Swagger

## Использование:

В данный момент приложение еще находится в стадии разработки, но уже реализован основной функционал, который можно протестировать.

Для запуска приложения необходимо будет выполнить следующее:

1. Склонировать проект на свою локальную машину

    ```git clone https://github.com/TiRastaMafia/JavaSpringProject.git```
2. В терминале перейти в директорию с проектом ```cd ~/JavaSpringProject```
3. Перед запуском приложения необзодимо запустить контейнер с базой данных, для этого потребуется выполнить всего 2 команды:
   - ```sudo docker image build -t imagedb .``` - создаст образ с базой данных по инструкции из `Dockerfile`, который наодится в проекте
   - ```sudo docker run --name clientsdb -p 6432:5432 -d imagedb``` - запустит контейнер с базой данных, которая будет доступна на порту 6432

    При желании можно настроить любой порт, какой вы сами захотите, но для этого необходимо будет внести изменения в файл `application.properties`
4. Теперь можно переходить в основной класс `RestExampleApplication.java` и запускать приложение, которое будет доступно на `localhost:8080`

    Стоит отметить, что вас сразу перекинет на страницу авторизации, поэтому для начала стоит перейти по ссылке http://localhost:8080/swagger-ui/index.html#/
    и создать нового пользователя.
5. Для тестирования рекомендуется создать пользователя с правами Администратора, чтобы были доступны все методы
   и можно было более детально разобраться, как устроено приложение.  
   - `POST /new-moderator` - передав в тело запроса 
     - Имя, 
     - Номер телефона в формате 8ХХХХХХХХХХ
     - Пароль не менее 6 символов, 
     с использоваением латинских строчных и заглавных букв, а также цифр и спецсимволов, вы создадите пользователя, под которым сможете авторизоваться
   - Далее http://localhost:8080/login - форма авторизации, требуется ввести номер телефона вашего пользователя и, созданный вами, пароль
   - После авторизации все методы по ссылке http://localhost:8080/swagger-ui/index.html#/ станут доступны

## Реализованный функцинал:

### Товары:
 - Добавить товар
 - Обновить информацию о товаре
 - Получить список всех товаров
 - Получить один товар по идентификатору
 - Удалить товар

### Услуги:
- Добавить услугу
- Обновить информацию об услуге
- Получить список всех услуг
- Получить одну услугу по идентификатору
- Удалить услугу

### Пользователи (клиенты):
- Создание нового пользователя
- Обновление информации по пользователю
- Получение списка всех пользователей
- Получение одного пользователя по идентификатору
- Фильтр пользователей по "Полу"
- Поиск пользователя по номеру телефона
- Удаление пользователя

### Пользователи (модераторы):
- Создание нового пользователя
- Обновление информации по пользователю
- Получение списка всех пользователей
- Получение одного пользователя по идентификатору
- Поиск пользователя по номеру телефона
- Удаление пользователя

### Заказы:
- Создание заказа
- Получение информации по одному заказу
- Получение списка всех заказов
- Изменение заказа
- Фильтр заказов по статусу заказа
- Оплата заказа
- Удаление заказа

### [Схемы](https://drive.google.com/file/d/18z176ypvMXkI3s8xC5Zz5Z14JXziVsec/view?usp=sharing), составленные для разработки приложение



## Планируемые доработки:

- Написание Unit-тестов
- Написание интеграционных тестов
- Обработка исключений
- Создание собственного метода для авторизации с получением  JWT-токена


