package ru.SimbirSoft.listeners;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.SimbirSoft.Driver.BrowserManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ScreenshotListener implements TestWatcher {

    private BrowserManager browserManager;

    // Конструктор для передачи BrowserManager
    public ScreenshotListener(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.err.println("Test failed: " + context.getDisplayName());

        if (browserManager != null && browserManager.getDriver() != null) {
            try {
                // Создаем скриншот и сохраняем в Allure
                captureScreenshotForAllure(browserManager.getDriver(), context.getDisplayName());

                // Также сохраняем в файл в allure-results директории
                saveScreenshotToFile(browserManager.getDriver(), context.getDisplayName());

            } catch (Exception e) {
                System.err.println("Could not capture screenshot: " + e.getMessage());
            }
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] captureScreenshotForAllure(WebDriver driver, String testName) {
        if (driver instanceof TakesScreenshot) {
            System.out.println("Capturing screenshot for Allure: " + testName);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    private void saveScreenshotToFile(WebDriver driver, String testName) {
        if (driver instanceof TakesScreenshot) {
            try {
                // Создаем директорию если не существует
                Path allureResultsDir = Paths.get("ru.SimbirSoft.allure-results");
                if (!Files.exists(allureResultsDir)) {
                    Files.createDirectories(allureResultsDir);
                }

                // Генерируем имя файла
                String fileName = "screenshot-" + testName.replaceAll("[^a-zA-Z0-9]", "-") + "-" + System.currentTimeMillis() + ".png";
                Path screenshotPath = allureResultsDir.resolve(fileName);

                // Сохраняем скриншот
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Files.write(screenshotPath, screenshot);

                System.out.println("Screenshot saved to: " + screenshotPath.toAbsolutePath());

            } catch (IOException e) {
                System.err.println("Failed to save screenshot to file: " + e.getMessage());
            }
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("Test passed: " + context.getDisplayName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("Test disabled: " + context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("Test aborted: " + context.getDisplayName());
    }
}