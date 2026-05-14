package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;
import java.time.Duration;

public class kırkdort {

    WebDriver driver = Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Kodu yavaşlatmak için kullanılacak yardımcı metot
    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g44. the user navigates to the product page {string}")
    public void the_user_navigates_to_the_product_page(String url) {
        driver.get(url);
        yavaslat(2000);
    }

    @Given("g44. the user clicks the add to cart button for the product")
    public void the_user_clicks_the_add_to_cart_button_for_the_product() {
        // Sepete ekle butonu için önceki koddaki xpath'i buraya kullanabilirsin
        WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
        yavaslat(1000);
        js.executeScript("arguments[0].click();", addToCartBtn);
        yavaslat(2000);
    }

    @When("g44. the user navigates to the payment page {string}")
    public void the_user_navigates_to_the_payment_page(String url) {
        driver.get(url);
        yavaslat(2000);
    }

    @When("g44. the user clicks the proceed to payment button without entering info")
    public void the_user_clicks_the_proceed_to_payment_button_without_entering_info() {
        // Ödeme sayfasındaki "Ödemeyi Tamamla / Devam Et" butonunun xpath'ini buraya giriniz
        WebElement proceedBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"place_order\"]")));

        // Butonu ortala ve zorunlu (JS) olarak tıkla
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", proceedBtn);
        yavaslat(1000);
        js.executeScript("arguments[0].click();", proceedBtn);
        yavaslat(3000); // Hatanın ekranda belirmesi için bekleme
    }

    @Then("g44. the payment error message should be displayed successfully")
    public void the_payment_error_message_should_be_displayed_successfully() {
        boolean isErrorDisplayed = false;
        try {
            // Çıkması gereken hatanın (Örn: Lütfen gerekli alanları doldurun) xpath'ini buraya giriniz
            String errorXpath = "//*[@id=\"post-1956\"]/div/div/form[3]/div[1]/div";

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorXpath)));
            isErrorDisplayed = errorMessage.isDisplayed();

            // Eğer hata mesajı görünürse test PASSED olur
            Assert.assertTrue(isErrorDisplayed, "Hata mesajı görünmedi! Test FAILED.");
            System.out.println("Test PASSED: Boş bilgilerle ödeme denemesi engellendi ve hata görüldü.");

        } catch (Exception e) {
            // Hata mesajı bulunamazsa isErrorDisplayed false kalacak ve test Fail olacaktır
            Assert.fail("Hata mesajı belirlenen sürede bulunamadı! Test FAILED.");
        }
    }
}