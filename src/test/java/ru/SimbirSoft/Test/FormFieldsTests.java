package ru.SimbirSoft.Test;

import io.qameta.allure.*;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import ru.SimbirSoft.Driver.BrowserManager;
import ru.SimbirSoft.Pages.FormFieldsPage;
import ru.SimbirSoft.config.AppConfig;
import ru.SimbirSoft.listeners.AllureTestListener;
import ru.SimbirSoft.listeners.AllureUtils;
import ru.SimbirSoft.listeners.ScreenshotListener;

@ExtendWith(ScreenshotListener.class)
@Epic("UI Automation")
@Feature("Form Fields")
@ExtendWith(AllureTestListener.class)
public class FormFieldsTests {

    private BrowserManager browserManager;
    private FormFieldsPage formFieldsPage;

    // Регистрируем слушатель с передачей browserManager
    @RegisterExtension
    ScreenshotListener screenshotListener = new ScreenshotListener(browserManager);

    @BeforeEach
    void setUp() {
        browserManager = new BrowserManager();
        formFieldsPage = new FormFieldsPage(browserManager);
        browserManager.openUrl(AppConfig.BASE_URL);
        browserManager.waitForPageLoad();

        // Обновляем ссылку на browserManager в слушателе
        screenshotListener = new ScreenshotListener(browserManager);
    }

    @AfterEach
    void tearDown() {
        if (browserManager != null) {
            browserManager.quitDriver();
        }
    }

/**
 * Тестовый класс для проверки функциональности формы
 */
    @Test
    @Story("Successful Form Submission")
    @DisplayName("Успешная отправка формы с валидными данными")
    @Description("Тест проверяет успешную отправку формы со всеми заполненными полями")
    @Severity(SeverityLevel.CRITICAL)
    void testSuccessfulFormSubmission() {
        // Генерация тестовых данных
        String name = formFieldsPage.generateRandomName();
        String password = formFieldsPage.generateRandomPassword();
        String email = formFieldsPage.generateEmail(name);
        String message = formFieldsPage.getAutomationToolsMessage();

        AllureUtils.addParameter("Имя", name);
        AllureUtils.addParameter("Email", email);
        AllureUtils.addParameter("Сообщение", message);

        // Заполняем форму
        formFieldsPage
                .enterName(name)
                .enterPassword(password)
                .selectMilkAndCoffee()
                .selectYellowColor()
                .selectAutomationOption("Yes")
                .enterEmail(email)
                .enterMessage(message)
                .submitForm();

        AllureUtils.addScreenshot(browserManager.getDriver(), "Форма заполнена и отправлена");

        // Обрабатываем alert
        formFieldsPage.handleAlert("Message received!");

        AllureUtils.addStep("Форма успешно отправлена и alert обработан");
    }
}