package ru.SimbirSoft.Driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.SimbirSoft.config.AppConfig;
import ru.SimbirSoft.config.ChromeOptionsConfig;

import java.time.Duration;

/**
 * Класс для управления жизненным циклом WebDriver
 * Отвечает за инициализацию, настройку и закрытие браузера
 */
public class BrowserManager {

    private WebDriver driver; // Экземпляр WebDriver для управления браузером

    /**
     * Конструктор инициализирует драйвер и настраивает браузер
     * Выполняется при создании экземпляра BrowserManager
     */
    public BrowserManager() {
        // Создаем ChromeDriver с настройками из ChromeOptionsConfig
        this.driver = WebDriverFactory.createChromeDriver(ChromeOptionsConfig.getChromeOptions());

        // Настраиваем браузер после создания
        maximizeWindow();     // Максимизируем окно браузера
        setImplicitWait(AppConfig.IMPLICIT_WAIT); // Устанавливаем неявное ожидание

    }

    /**
     * Возвращает экземпляр WebDriver для использования в тестах
     * @return текущий экземпляр WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Открывает указанный URL в браузере
     * @param url адрес страницы для открытия
     */
    public void openUrl(String url) {
        if (driver != null) {
            driver.get(url); // Выполняем переход по URL
        }
    }

    /**
     * Максимизирует окно браузера для полного отображения страницы
     */
    public void maximizeWindow() {
        if (driver != null) {
            driver.manage().window().maximize(); // Вызов метода максимизации окна
        }
    }

    /**
     * Устанавливает неявное ожидание для всех операций поиска элементов
     * @param timeoutInSeconds время ожидания в секундах
     */
    public void setImplicitWait(long timeoutInSeconds) {
        if (driver != null) {
            // Устанавливаем неявное ожидание через Duration
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
        }
    }

    /**
     * Ожидание полной загрузки страницы
     * Проверяет состояние readyState документа
     */
    public void waitForPageLoad() {
        if (driver != null) {
            // Создаем явное ожидание с увеличенным таймаутом
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConfig.PAGE_LOAD_WAIT));

            // Ожидаем, пока документ не перейдет в состояние "complete"
            wait.until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        }
    }

    /**
     * Закрывает браузер и освобождает ресурсы
     * Должен вызываться после каждого теста
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();    // Полное закрытие браузера и процесса
            driver = null;    // Обнуляем ссылку для сборки мусора
        }
    }

}