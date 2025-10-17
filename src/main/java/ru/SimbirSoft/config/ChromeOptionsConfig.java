package ru.SimbirSoft.config;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Класс для настройки опций браузера Chrome
 * Инкапсулирует все настройки ChromeDriver
 */
public class ChromeOptionsConfig {

    /**
     * Создает и возвращает предварительно настроенные опции для Chrome
     * @return ChromeOptions с настроенными параметрами
     */
    public static ChromeOptions getChromeOptions() {
        // Создаем объект для хранения опций Chrome
        ChromeOptions options = new ChromeOptions();

        // Базовые опции для стабильной работы
        options.addArguments("--no-sandbox"); // Отключает sandbox режим
        options.addArguments("--disable-dev-shm-usage"); // Отключает использование shared memory
        options.addArguments("--remote-allow-origins=*"); // Разрешает все origins

        // Опции для отображения
        options.addArguments("--disable-gpu"); // Отключает GPU ускорение
        options.addArguments("--window-size=1920,1080"); // Устанавливает размер окна браузера

        // Убедимся что окно максимальное
        options.addArguments("--start-maximized");

        // Headless режим (раскомментировать для CI/CD)
        // options.addArguments("--headless");

        return options; // Возвращаем настроенные опции
    }
}