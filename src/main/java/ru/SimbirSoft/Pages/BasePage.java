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

        try {
            action.run();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Если элемент перекрыт, используем JavaScript для клика
            System.out.println("Element click intercepted, using JavaScript click");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    protected void click(WebElement element) {
        performAction(element, element::click);
    }

    protected void type(WebElement element, String text) {
        performAction(element, () -> {
            element.clear();
            element.sendKeys(text);
        });
    }

    // Добавляем метод для скролла к элементу
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

}