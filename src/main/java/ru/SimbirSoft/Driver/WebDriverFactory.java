package ru.SimbirSoft.Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Фабрика для создания экземпляров WebDriver
 * Реализует паттерн Factory Method
 */
public class WebDriverFactory {

    /**
     * Создает и возвращает экземпляр ChromeDriver с заданными опциями
     * @param options настройки браузера Chrome
     * @return экземпляр WebDriver для управления браузером
     */
    public static WebDriver createChromeDriver(ChromeOptions options) {

        // Создаем новый экземпляр ChromeDriver с переданными опциями
        return new ChromeDriver(options);
    }
}