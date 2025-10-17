package ru.SimbirSoft.Pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.SimbirSoft.Driver.BrowserManager;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class FormFieldsPage extends BasePage {

    private final Faker faker = new Faker(new Locale("en-EN"));

    // === ЛОКАТОРЫ ===

    @FindBy(id = "name-input")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(id = "drink2")
    private WebElement milkCheckbox;

    @FindBy(id = "drink3")
    private WebElement coffeeCheckbox;

    @FindBy(id = "color3")
    private WebElement yellowRadio;

    @FindBy(id = "automation")
    private WebElement automationDropdown;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "message")
    private WebElement messageTextarea;

    @FindBy(id = "submit-btn")
    private WebElement submitButton;

    public FormFieldsPage(BrowserManager browserManager) {
        super(browserManager);
    }

    // === FLUENT INTERFACE МЕТОДЫ ===

    public FormFieldsPage enterName(String name) {
        type(nameInput, name);
        return this;
    }

    public FormFieldsPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public FormFieldsPage selectMilkAndCoffee() {
        click(milkCheckbox);
        click(coffeeCheckbox);
        return this;
    }

    public FormFieldsPage selectYellowColor() {
        click(yellowRadio);
        return this;
    }

    public FormFieldsPage selectAutomationOption(String optionText) {
        Select select = new Select(automationDropdown);
        select.selectByVisibleText(optionText);
        return this;
    }

    public FormFieldsPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    public String getAutomationToolsMessage() {
        List<String> automationTools = List.of("Selenium", "Playwright", "Cypress", "Appium", "Katalon Studio");
        int numberOfTools = automationTools.size();
        String longestTool = automationTools.stream()
                .max((tool1, tool2) -> Integer.compare(tool1.length(), tool2.length()))
                .orElse("");
        return String.format("%d, %s", numberOfTools, longestTool);
    }

    public FormFieldsPage enterMessage(String message) {
        type(messageTextarea, message);
        return this;
    }

    public FormFieldsPage submitForm() {
        click(submitButton);

        // Ожидаем появления алерта и выводим его текст
        waitForAlertMessage();

        return this;
    }

    private void waitForAlertMessage() {
        try {
            // Ждем появления алерта до 10 секунд
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());

            // Переключаемся на алерт и получаем текст
            String alertText = driver.switchTo().alert().getText();
            System.out.println("=== ALERT DETECTED ===");
            System.out.println("Alert text: " + alertText);
            System.out.println("=== ALERT WILL REMAIN OPEN ===");

            // Ждем 5 секунд чтобы можно было увидеть алерт
            Thread.sleep(5000);


        } catch (org.openqa.selenium.NoAlertPresentException e) {
            System.out.println("No alert present after form submission");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for alert");
        } catch (Exception e) {
            System.out.println("Error handling alert: " + e.getMessage());
        }
    }

    // === МЕТОДЫ ДЛЯ РАБОТЫ С ALERT ===

    /**
     * Получает текст из alert сообщения
     */
    public String getSuccessMessage() {
        try {
            // Для алертов используем другой подход
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());

            String alertText = driver.switchTo().alert().getText();
            return alertText;

        } catch (Exception e) {
            System.err.println("Alert message not found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Проверяет, отображается ли alert с успешным сообщением
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Генераторы данных остаются без изменений
    public String generateRandomName() {
        return faker.name().fullName();
    }

    public String generateRandomPassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public String generateEmail(String name) {
        return name.toLowerCase().replaceAll("\\s+", "") + "@example.com";
    }

}