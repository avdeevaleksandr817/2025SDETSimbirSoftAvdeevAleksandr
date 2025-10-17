package ru.SimbirSoft.Test;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.SimbirSoft.Driver.BrowserManager;
import ru.SimbirSoft.Pages.FormFieldsPage;
import ru.SimbirSoft.config.AppConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности формы
 * Использует JUnit 5 и Allure для отчетности
 */
@Epic("UI Automation") // Allure эпик - основная функциональная область
@Feature("Form Fields") // Allure фича - конкретная функциональность

public class FormFieldsTests {

    private BrowserManager browserManager; // Менеджер браузера для управления WebDriver
    private FormFieldsPage formFieldsPage; // Page Object для взаимодействия с формой

    /**
     * Метод выполняется ПЕРЕД КАЖДЫМ тестом
     * Инициализирует браузер и открывает тестовую страницу
     */
    @BeforeEach
    void setUp() {
        browserManager = new BrowserManager(); // Создаем новый менеджер браузера
        formFieldsPage = new FormFieldsPage(browserManager); // Создаем Page Object для формы
        browserManager.openUrl(AppConfig.BASE_URL); // Открываем тестовую страницу
        browserManager.waitForPageLoad(); // Ожидаем полной загрузки страницы

    }

    /**
     * Метод выполняется ПОСЛЕ КАЖДОГО теста
     * Закрывает браузер и освобождает ресурсы
     */
    @AfterEach
    void tearDown() {
        if (browserManager != null) {
            browserManager.quitDriver(); // Закрываем браузер и освобождаем ресурсы
        }
    }

    /**
     * ОСНОВНОЙ ПОЗИТИВНЫЙ ТЕСТ
     * Проверяет успешное заполнение и отправку формы со всеми валидными данными
     */
    @Test
    @Story("Successful Form Submission") // Allure история - конкретный сценарий
    @DisplayName("Успешная отправка формы с валидными данными") // Отображаемое имя теста
    @Description("Тест проверяет успешную отправку формы со всеми заполненными полями") // Описание теста

    @Severity(SeverityLevel.CRITICAL) // Важность теста - критический

        void testSuccessfulFormSubmission() {

        // Генерация тестовых данных
        String name = formFieldsPage.generateRandomName(); // Генерируем случайное имя
        String password = formFieldsPage.generateRandomPassword(); // Генерируем случайный пароль
        String email = formFieldsPage.generateEmail(name); // Генерируем email на основе имени
        String message = formFieldsPage.getAutomationToolsMessage(); // Создаем сообщение об инструментах

        // Заполнение формы с использованием Fluent Interface
        formFieldsPage

                .enterName(name) // 1. Заполнить поле Name
                .enterPassword(password) // 2. Заполнить поле Password
                .selectMilkAndCoffee() // 3. Выбрать Milk и Coffee
                .selectYellowColor() // 4. Выбрать Yellow цвет
                .selectAutomationOption("Yes") // 5. Выбрать опцию автоматизации
                .enterEmail(email) // 6. Заполнить Email
                .enterMessage(message) // 7. Ввести сообщение
                .submitForm(); // 8. Нажать Submit

        assertTrue(formFieldsPage.isSuccessMessageDisplayed(),
                "Success alert should be displayed after form submission");

        String actualMessage = formFieldsPage.getSuccessMessage();
        assertEquals("Message received!", actualMessage,
                "Success message text should be 'Message received!'");

    }

}