package ru.SimbirSoft.listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

/**
 * Утилиты для работы с Allure отчетами
 * Предоставляет методы для добавления скриншотов, текстовых вложений и исходного кода страниц
 */
public class AllureUtils {

    /**
     * Добавляет скриншот к Allure отчету
     * @param driver экземпляр WebDriver
     * @param name название скриншота в отчете
     */
    public static void addScreenshot(WebDriver driver, String name) {
        try {
            // Проверяем, есть ли активный alert
            try {
                driver.switchTo().alert();
                // Если есть alert, временно переключаемся обратно на основную страницу
                driver.switchTo().defaultContent();
            } catch (NoAlertPresentException e) {
                // Alert нет, продолжаем как обычно
            }

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
            System.out.println("📸 Скриншот добавлен: " + name);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании скриншота: " + e.getMessage());
        }
    }

    /**
     * Добавляет исходный код страницы к Allure отчету
     * @param driver экземпляр WebDriver
     * @param name название вложения в отчете
     */
    public static void addPageSource(WebDriver driver, String name) {
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment(name, "text/html", pageSource, ".html");
            System.out.println("📄 Исходный код страницы добавлен: " + name);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении исходного кода: " + e.getMessage());
        }
    }

    /**
     * Добавляет текстовое вложение к Allure отчету
     * @param name название вложения
     * @param content содержимое текста
     */
    public static void addTextAttachment(String name, String content) {
        try {
            Allure.addAttachment(name, "text/plain", content, ".txt");
            System.out.println("📝 Текстовое вложение добавлено: " + name);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при добавлении текстового вложения: " + e.getMessage());
        }
    }

    /**
     * Добавляет шаг с описанием в Allure отчет
     * @param stepName название шага
     */
    public static void addStep(String stepName) {
        try {
            Allure.step(stepName);
            System.out.println("🔹 Шаг выполнен: " + stepName);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при добавлении шага: " + e.getMessage());
        }
    }

    /**
     * Добавляет параметр теста в Allure отчет
     * @param name название параметра
     * @param value значение параметра
     */
    public static void addParameter(String name, String value) {
        try {
            Allure.parameter(name, value);
            System.out.println("📋 Параметр добавлен: " + name + " = " + value);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при добавлении параметра: " + e.getMessage());
        }
    }
}