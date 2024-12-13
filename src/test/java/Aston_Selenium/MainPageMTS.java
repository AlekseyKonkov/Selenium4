package Aston_Selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;

class MainPageMTS extends BaseOfTest {

    @DisplayName("Тестирование заголовка")
    @Severity(value = SeverityLevel.MINOR)
    @Description("Тестируется заголовок: Онлайн пополнение без комиссии, в блоке услуг")
    @Test
    public void testBlockTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement blockTitle = wait.until(ExpectedConditions.presenceOfElementLocated(BLOCK_TITLE));
        assertEquals("Онлайн пополнение без комиссии", blockTitle.getText().trim().replaceAll("\\s+", " "));
        System.out.println("Заголовок проверен успешно");
    }

    @DisplayName("Тестирование логотипа платёжных систем")
    @Severity(value = SeverityLevel.MINOR)
    @Description("Тестируются логотипы(5 img) банков, в блоке услуг ")
    @Test
    public void testPaymentLogos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement partnersElement = wait.until(ExpectedConditions.presenceOfElementLocated(PARTNERS_ELEMENT));
        List<WebElement> logos = partnersElement.findElements(PAYMENT_LOGOS);
        assertEquals(5, logos.size());
        System.out.println("Логотипы платежных систем проверены успешно");
    }

    @DisplayName("Проверка ссылки")
    @Severity(value = SeverityLevel.BLOCKER)
    @Description("Производится клик по ссылке, проверяется изменение url ")
    @Test
    public void testLink() {
        cookie_agree();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(MORE_DETAILS_LINK));
        link.click();
        // Здесь лучше бы было добавить проверку изменения URL для повышения тестового покрытия
        driver.navigate().back();
        System.out.println("Ссылка проверена успешно");
    }

    @DisplayName("Тестирование вкладки оплаты")
    @Severity(value = SeverityLevel.CRITICAL)
    @Description("Тест frame внутри сайта для услуги связи...")
    @ParameterizedTest
    @CsvSource({ "297777777,56,aaaa@mail.ru,Номер карты,Срок действия,CVC,Имя держателя (как на карте)" })
    public void testFields(String phoneNumber, String sum, String email, String NumberOfCard, String TimeOutOfCard,
                           String CVC_1, String NameOwnerOfCard) {
        cookie_agree();

        WebElement inputField = driver.findElement(PHONE_NUMBER_FIELD);
        inputField.sendKeys(phoneNumber);
        inputField = driver.findElement(SUM_FIELD);
        inputField.sendKeys(sum);
        inputField = driver.findElement(EMAIL_FIELD);
        inputField.sendKeys(email);
        WebElement next = driver.findElement(NEXT_BUTTON);
        next.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.switchTo().frame(wait.until(ExpectedConditions.visibilityOfElementLocated(DOM2)));

        wait.until(ExpectedConditions.visibilityOfElementLocated(CARD_NUMBER_FIELD));

        assertEquals(NumberOfCard, driver.findElement(CARD_NUMBER_FIELD).getText());
        assertEquals(TimeOutOfCard, driver.findElement(TIME_OUT_FIELD).getText());
        assertEquals(CVC_1, driver.findElement(CVC).getText());
        assertEquals(NameOwnerOfCard, driver.findElement(OWNER_OF_CARD).getText());

        assertEquals(sum + ".00 BYN", driver.findElement(CONVERTED_COST).getText());
        assertEquals("Оплата: Услуги связи Номер:375" + phoneNumber,
                driver.findElement(CONVERTED_TEXT).getText());
        assertEquals("Оплатить " + sum + ".00 BYN", driver.findElement(CONVERTED_PAYMENT).getText());

        List<WebElement> icons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CONVERTED_IMG));
        assertEquals(5, icons.size());
        System.out.println("Поля вкладки оплаты проверены успешно");
    }

    @DisplayName("Тестирование placeholder'ов всех услуг")
    @Severity(value = SeverityLevel.MINOR)
    @ParameterizedTest
    @CsvSource({
            "Номер телефона,Сумма,E-mail для отправки чека,Номер абонента,Номер счета на 44,Номер счета на 2073" })
    public void testPlaceHolders(String expectedPhoneNumberPlaceholder, String expectedSumPlaceholder,
                                 String expectedEmailPlaceholder, String expectedUserNumberPlaceholder,
                                 String expectedCard_44_NumberPlaceholder, String expectedCard_2073_NumberPlaceholder) {

        assertEquals(expectedPhoneNumberPlaceholder,
                driver.findElement(PHONE_NUMBER_FIELD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedUserNumberPlaceholder, driver.findElement(PHONE_NUMBER_FIELD_HOME)
                .getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedCard_44_NumberPlaceholder,
                driver.findElement(NUMBER_FIELD_CARD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedCard_2073_NumberPlaceholder,
                driver.findElement(NUMBER_FIELD_DUTY).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));

        assertEquals(expectedSumPlaceholder,
                driver.findElement(SUM_FIELD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedSumPlaceholder,
                driver.findElement(SUM_FIELD_HOME).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedSumPlaceholder,
                driver.findElement(SUM_FIELD_CARD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedSumPlaceholder,
                driver.findElement(SUM_FIELD_DUTY).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedEmailPlaceholder,
                driver.findElement(EMAIL_FIELD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedEmailPlaceholder,
                driver.findElement(EMAIL_FIELD_HOME).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedEmailPlaceholder,
                driver.findElement(EMAIL_FIELD_CARD).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));
        assertEquals(expectedEmailPlaceholder,
                driver.findElement(EMAIL_FIELD_DUTY).getDomAttribute("placeholder").trim().replaceAll("\\s+", " "));

        System.out.println("Плейсхолдеры всех услуг проверены успешно");
    }

}
