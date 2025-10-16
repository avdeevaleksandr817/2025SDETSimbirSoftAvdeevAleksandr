package ru.SimbirSoft.Pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.SimbirSoft.Driver.BrowserManager;

import java.util.List;
import java.util.Locale;

public class FormFieldsPage extends BasePage {

    private final Faker faker = new Faker(new Locale("en-EN"));

    // === СУЩЕСТВУЮЩИЕ ЛОКАТОРЫ ===

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

    public FormFieldsPage enterName(String name) {
        scrollToElement(nameInput);
        type(nameInput, name);
        return this;
    }

    public FormFieldsPage enterPassword(String password) {
        scrollToElement(passwordInput);
        type(passwordInput, password);
        return this;
    }

    public FormFieldsPage selectMilkAndCoffee() {
        scrollToElement(milkCheckbox);
        click(milkCheckbox);
        scrollToElement(coffeeCheckbox);
        click(coffeeCheckbox);
        return this;
    }

    public FormFieldsPage selectYellowColor() {
        scrollToElement(yellowRadio);
        click(yellowRadio);
        return this;
    }

    public FormFieldsPage selectAutomationOption(String optionText) {
        scrollToElement(automationDropdown);
        Select select = new Select(automationDropdown);
        select.selectByVisibleText(optionText);
        return this;
    }

    public FormFieldsPage enterEmail(String email) {
        scrollToElement(emailInput);
        type(emailInput, email);
        return this;
    }

    public FormFieldsPage enterMessage(String message) {
        scrollToElement(messageTextarea);
        type(messageTextarea, message);
        return this;
    }

    public FormFieldsPage submitForm() {
        scrollToElement(submitButton);
        click(submitButton);
        return this;
    }
    protected void click(WebElement element) {
        try {
            performAction(element, element::click);
        } catch (Exception e) {
            // Резервный вариант через JavaScript
            System.out.println("Element click intercepted, using JavaScript click");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Простой handleAlert без параметров (принимает alert)
     */
    public void handleAlert(String action) {
        // Реализация с параметром action (accept, dismiss, etc.)
        try {
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            if (action == null || "accept".equalsIgnoreCase(action)) {
                alert.accept(); // Принимаем алерт по умолчанию
            } else if ("dismiss".equalsIgnoreCase(action)) {
                alert.dismiss(); // Отклоняем алерт
            } else {
                alert.accept(); // По умолчанию принимаем
            }
            System.out.println("Alert handled with action: " + (action != null ? action : "accept"));
        } catch (Exception e) {
            System.out.println("No alert present or error handling alert: " + e.getMessage());
        }
    }

    // Перегруженный метод без параметров для обратной совместимости
    public void handleAlert() {
        handleAlert("accept"); // Вызываем с действием по умолчанию
    }
    // Создание сообщения об инструментах автоматизации
    public String getAutomationToolsMessage() {
        List<String> automationTools = List.of("Selenium", "Playwright", "Cypress", "Appium", "Katalon Studio");

        int numberOfTools = automationTools.size(); // Количество инструментов

        // Поиск инструмента с максимальной длиной
        String longestTool = automationTools.stream()
                .max((tool1, tool2) -> Integer.compare(tool1.length(), tool2.length()))
                .orElse("");

        // Просто число и название самого длинного инструмента через запятую
        return numberOfTools + ", " + longestTool;
    }

    // Генераторы данных
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