# UI Automation Project

## Описание проекта
Проект автоматизации UI-тестов для формы на сайте [practice-automation.com](https://practice-automation.com). Реализованы позитивные и негативные сценарии тестирования формы с использованием современных паттернов автоматизации.

## Технологии
- Java 17
- Selenium WebDriver 4.17.0
- JUnit 5
- Gradle
- Allure Reports
- Faker (генерация тестовых данных)

## Архитектура
- Page Object Model - для работы с элементами страницы
- Page Factory - для инициализации элементов
- Fluent Interface - для цепочек вызовов
- Allure - для генерации отчетов

## Тест-кейсы

### Позитивный тест-кейс ✅
**Название:** Успешная отправка формы с альтернативными валидными данными

#### Предусловия:
- Браузер Chrome запущен
- Открыта страница [https://practice-automation.com/form-fields/](https://practice-automation.com/form-fields/)

#### Шаги:
1. Заполнить поле Name: “Test User”
2. Заполнить поле Password: “SecurePass456”
3. В разделе “What is your favorite drink?” выбрать опции “Water” и “Wine”
4. В разделе “What is your favorite color?” выбрать опцию “Blue”
5. В выпадающем списке “Do you like automation?” выбрать опцию “No”
6. В разделе “Automation tools” выбрать инструменты “Playwright” и “Appium”
7. Заполнить поле Email: “testuser@domain.com”
8. В поле Message ввести: “Testing alternative valid data combination”
9. Нажать на кнопку “Submit”

#### Ожидаемый результат:
- Появляется алерт с текстом “Message received!”
- Форма успешно отправлена с альтернативным набором данных
- Подтверждается гибкость валидации формы

### Негативный тест-кейс ❌
**Название:** Попытка отправки формы с пустым обязательным полем Name

#### Предусловия:
- Браузер Chrome запущен
- Открыта страница [https://practice-automation.com/form-fields/](https://practice-automation.com/form-fields/)

#### Шаги:
1. Оставить поле Name ПУСТЫМ
2. Заполнить поле Password: “TestPassword123”
3. В разделе “What is your favorite drink?” выбрать опции “Milk” и “Coffee”
4. В разделе “What is your favorite color?” выбрать опцию “Yellow”
5. В выпадающем списке “Do you like automation?” выбрать опцию “Yes”
6. В разделе “Automation tools” выбрать инструмент “Selenium”
7. Заполнить поле Email: “test@example.com”
8. В поле Message ввести: “Testing empty name field validation”
9. Нажать на кнопку “Submit”

#### Ожидаемый результат:
- Сообщение “Message received!” НЕ появляется
- Форма НЕ отправляется
- Демонстрируется работа валидации обязательных полей
- Поле Name помечается как обязательное для
