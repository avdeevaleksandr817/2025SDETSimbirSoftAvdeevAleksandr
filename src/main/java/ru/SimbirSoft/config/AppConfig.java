package ru.SimbirSoft.config;

/**
 * Класс для хранения конфигурационных констант приложения
 * Содержит базовые настройки, такие как URL тестируемого сайта
 */
public class AppConfig {
    // Базовый URL тестируемого приложения - страница с формой
    public static final String BASE_URL = "https://practice-automation.com/form-fields/";

    // Таймауты для ожиданий (можно вынести в конфиг)
    public static final long IMPLICIT_WAIT = 10; // Неявное ожидание в секундах
    public static final long EXPLICIT_WAIT = 15; // Явное ожидание в секундах
    public static final long PAGE_LOAD_WAIT = 30; // Ожидание загрузки страницы
}
