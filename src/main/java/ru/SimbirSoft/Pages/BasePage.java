package ru.SimbirSoft.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.SimbirSoft.Driver.BrowserManager;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(BrowserManager browserManager) {
        this.driver = browserManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected void performAction(WebElement element, Runnable action) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        // Прокручиваем к элементу перед действием
        scrollToElement(element);

        action.run();
    }

    protected void click(WebElement element) {
        try {
            performAction(element, element::click);
        } catch (Exception e) {
            // Если обычный клик не работает, используем JavaScript клик
            System.err.println("Regular click failed, using JavaScript click: " + e.getMessage());
            javascriptClick(element);
        }
    }

    protected void type(WebElement element, String text) {
        performAction(element, () -> {
            element.clear();
            element.sendKeys(text);
        });
    }

    // Прокрутка к элементу с центрированием
    protected void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});",
                    element
            );
            // Даем время для завершения прокрутки
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Scroll to element failed: " + e.getMessage());
        }
    }

    // JavaScript клик как запасной вариант
    protected void javascriptClick(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.err.println("JavaScript click also failed: " + e.getMessage());
            throw e;
        }
    }
}