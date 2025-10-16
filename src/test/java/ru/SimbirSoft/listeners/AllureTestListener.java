package ru.SimbirSoft.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

/**
 * Расширенный слушатель тестов для интеграции с Allure
 * Автоматически создает скриншоты и прикрепляет их к отчетам
 */
public class AllureTestListener implements TestWatcher {

    // ThreadLocal для хранения WebDriver в многопоточных сценариях
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Устанавливает WebDriver для текущего потока
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
        System.out.println("🚗 WebDriver установлен для потока: " + Thread.currentThread().getName());
    }

    /**
     * Очищает WebDriver для текущего потока
     */
    public static void clearDriver() {
        driverThreadLocal.remove();
        System.out.println("🧹 WebDriver очищен для потока: " + Thread.currentThread().getName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        System.err.println("❌ Тест упал: " + testName);

        // Добавляем информацию об ошибке
        AllureUtils.addTextAttachment("Ошибка теста",
                "Тест: " + testName + "\n" +
                        "Ошибка: " + cause.getMessage() + "\n" +
                        "Тип ошибки: " + cause.getClass().getSimpleName() + "\n" +
                        "Время: " + java.time.LocalDateTime.now()
        );

        // Создаем скриншот и прикрепляем исходный код страницы
        captureScreenshot("СКРИНШОТ ПРИ ОШИБКЕ: " + testName);
        attachPageSource("ИСХОДНЫЙ КОД СТРАНИЦЫ ПРИ ОШИБКЕ");

        // Логируем стектрейс для отладки
        System.err.println("Stack trace:");
        cause.printStackTrace();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        System.out.println("✅ Тест прошел успешно: " + testName);

        // Добавляем скриншот успешного выполнения
        captureScreenshot("СКРИНШОТ УСПЕШНОГО ВЫПОЛНЕНИЯ: " + testName);

        // Добавляем информацию об успешном выполнении
        AllureUtils.addTextAttachment("Результат теста",
                "Тест: " + testName + "\n" +
                        "Статус: УСПЕШНО\n" +
                        "Время выполнения: " + java.time.LocalDateTime.now()
        );
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        System.out.println("⏸️ Тест прерван: " + testName);

        AllureUtils.addTextAttachment("Тест прерван",
                "Тест: " + testName + "\n" +
                        "Причина: " + cause.getMessage() + "\n" +
                        "Время: " + java.time.LocalDateTime.now()
        );

        captureScreenshot("СКРИНШОТ ПРИ ПРЕРЫВАНИИ: " + testName);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String testName = context.getDisplayName();
        String reasonText = reason.orElse("Причина не указана");
        System.out.println("🚫 Тест отключен: " + testName + " - " + reasonText);

        AllureUtils.addTextAttachment("Тест отключен",
                "Тест: " + testName + "\n" +
                        "Причина: " + reasonText + "\n" +
                        "Время: " + java.time.LocalDateTime.now()
        );
    }

    /**
     * Создает скриншот и прикрепляет его к Allure отчету
     */
    private void captureScreenshot(String screenshotName) {
        try {
            WebDriver driver = getWebDriver();
            if (driver != null && driver instanceof TakesScreenshot) {
                AllureUtils.addScreenshot(driver, screenshotName);
            } else {
                System.err.println("Не удалось создать скриншот: WebDriver недоступен");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании скриншота: " + e.getMessage());
        }
    }

    /**
     * Прикрепляет исходный код страницы к Allure отчету
     */
    private void attachPageSource(String attachmentName) {
        try {
            WebDriver driver = getWebDriver();
            if (driver != null) {
                AllureUtils.addPageSource(driver, attachmentName);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при прикреплении исходного кода: " + e.getMessage());
        }
    }

    /**
     * Получает WebDriver из ThreadLocal
     */
    private WebDriver getWebDriver() {
        return driverThreadLocal.get();
    }
}