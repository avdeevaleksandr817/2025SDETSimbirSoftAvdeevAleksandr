package ru.SimbirSoft.Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Фабрика для создания экземпляров WebDriver
 * Реализует паттерн Factory Method для инкапсуляции логики создания драйвера
 */
public class WebDriverFactory {

    /**
     * Создает и возвращает экземпляр ChromeDriver с заданными опциями
     * @param options настройки браузера Chrome
     * @return экземпляр WebDriver для управления браузером
     */
    public static WebDriver createChromeDriver(ChromeOptions options) {
        // Логирование начала создания драйвера
        System.out.println("Creating ChromeDriver instance with configured options...");

        // Создаем новый экземпляр ChromeDriver с переданными опциями
        WebDriver driver = new ChromeDriver(options);

        // Логирование успешного создания
        System.out.println("ChromeDriver successfully created");

        return driver; // Возвращаем созданный драйвер
    }
}
