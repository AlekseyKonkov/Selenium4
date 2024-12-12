package AstonSelenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class MTS_Test {

    private static WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); 
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://www.mts.by");
        cookie_agree();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".cookie.show")));

    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static void cookie_agree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cookie-agree")));
        cookieButton.click();
    }


    @DisplayName("Тестирование заголовка")
    @Test
    void testBlockTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String expectedTitle = "Онлайн пополнение без комиссии";
        WebElement blockTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[normalize-space()='Онлайн пополнение без комиссии']")));
        assertEquals(expectedTitle, blockTitle.getText().trim().replaceAll("\\s+", " "));
    }

    @DisplayName("Тестирование логотипа платёжных систем")
    @Test
    void testPaymentLogos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement partnersElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pay__partners")));
        List<WebElement> logos = partnersElement.findElements(By.tagName("img"));
        assertEquals(5, logos.size());
    }

    @DisplayName("Проверка ссылки")
    @Test
    void testLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
        link.click();
    }

    @DisplayName("Тестирование заполнения формы")
    @ParameterizedTest
    @CsvSource({
            "297777777,100,aaa.aaa@aaa.ru"
    })
    void testFields(String phoneNumber, String sum, String email) {
        WebElement inputField = driver.findElement(By.id("connection-phone"));
        inputField.sendKeys(phoneNumber);
        inputField = driver.findElement(By.id("connection-sum"));
        inputField.sendKeys(sum);
        inputField = driver.findElement(By.id("connection-email"));
        inputField.sendKeys(email);
        WebElement next = driver.findElement(By.xpath("//*[text() = 'Продолжить']"));
        next.click();
    }
}






