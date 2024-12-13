package Aston_Selenium;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseOfTest {
    public static final String MTS_PAGE = "https://www.mts.by";
    public static final By DOM2 = By.cssSelector("iframe.bepaid-iframe");
    public static final By BLOCK_TITLE = By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2");
    public static final By PARTNERS_ELEMENT = By.cssSelector(".pay__partners");
    public static final By PAYMENT_LOGOS = By.xpath("//img[contains(@src, '/local/templates/new_design/assets/html/images/pages/index/pay')]");
    public static final By COOKIE_BUTTON = By.id("cookie-agree");
    public static final By MORE_DETAILS_LINK = By.linkText("Подробнее о сервисе");
    public static final By NEXT_BUTTON = By.xpath("//*[text() = 'Продолжить']");

    public static final By PHONE_NUMBER_FIELD = By.id("connection-phone");
    public static final By SUM_FIELD = By.id("connection-sum");
    public static final By EMAIL_FIELD = By.id("connection-email");

    public static final By PHONE_NUMBER_FIELD_HOME = By.id("internet-phone");
    public static final By SUM_FIELD_HOME = By.id("internet-sum");
    public static final By EMAIL_FIELD_HOME = By.id("internet-email");

    public static final By NUMBER_FIELD_CARD = By.id("score-instalment");
    public static final By SUM_FIELD_CARD = By.id("instalment-sum");
    public static final By EMAIL_FIELD_CARD = By.id("instalment-email");

    public static final By NUMBER_FIELD_DUTY = By.id("score-arrears");
    public static final By SUM_FIELD_DUTY = By.id("arrears-sum");
    public static final By EMAIL_FIELD_DUTY = By.id("arrears-email");

    public static final By CARD_NUMBER_FIELD = By.cssSelector("label.ng-tns-c46-1.ng-star-inserted");
    public static final By TIME_OUT_FIELD = By.cssSelector("label.ng-tns-c46-4.ng-star-inserted");
    public static final By CVC = By.cssSelector("label.ng-tns-c46-5.ng-star-inserted");
    public static final By OWNER_OF_CARD = By.cssSelector("label.ng-tns-c46-3.ng-star-inserted");

    public static final By CONVERTED_COST = By.cssSelector("div.pay-description__cost");
    public static final By CONVERTED_TEXT = By.cssSelector("div.pay-description__text");
    public static final By CONVERTED_IMG = By.xpath("//img[contains(@src, 'assets/images/payment-icons/card-types/')]");
    public static final By CONVERTED_PAYMENT = By.cssSelector("button.colored.disabled");

    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(MTS_PAGE);
        cookie_agree();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void cookie_agree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.presenceOfElementLocated(COOKIE_BUTTON));
            cookieButton.click();
        } catch (WebDriverException e) {
            System.err.println("Ошибка при клике на кнопку cookies: " + e.getMessage());
        }
    }
}
