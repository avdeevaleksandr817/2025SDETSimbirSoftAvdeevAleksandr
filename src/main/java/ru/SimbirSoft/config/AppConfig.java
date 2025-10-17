package ru.SimbirSoft.config;

/**
 * Класс для хранения конфигурационных констант приложения
 * Содержит базовые настройки проекта
 */
public class AppConfig {
    // Базовый URL тестируемого приложения - страница с формой
    public static final String BASE_URL = "https://practice-automation.com/form-fields/";

    // Таймаут для неявного ожидания элементов (в секундах)
    public static final long IMPLICIT_WAIT = 10;

    // Таймаут для ожидания загрузки страницы (в секундах)
    public static final long PAGE_LOAD_WAIT = 30;
}