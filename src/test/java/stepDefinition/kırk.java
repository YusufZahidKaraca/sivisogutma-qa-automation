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

public class kırk {

    WebDriver driver = Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g40. the user navigates to the product page to add item {string}")
    public void the_user_navigates_to_the_product_page_to_add_item(String url) {
        driver.get(url);
        yavaslat(2000);
    }

    @Given("g40. the user clicks the add to cart button")
    public void the_user_clicks_the_add_to_cart_button() {
        // Ürünü sepete ekleme butonunun xpath'ini buraya giriniz
        WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
        yavaslat(1000);
        js.executeScript("arguments[0].click();", addToCartBtn);
        yavaslat(2000); // Sepete eklenme işleminin tamamlanması için mola
    }

    @When("g40. the user navigates to the checkout or cart page {string}")
    public void the_user_navigates_to_the_checkout_or_cart_page(String url) {
        driver.get(url);
        yavaslat(2000);
    }

    @When("g40. the user enters an invalid coupon code {string}")
    public void the_user_enters_an_invalid_coupon_code(String couponCode) {
        // Kupon kodu yazılacak girdi alanının (input) xpath'ini buraya giriniz
        WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"coupon_code\"]")));
        couponInput.clear();
        couponInput.sendKeys(couponCode);
        yavaslat(1000);
    }

    @When("g40. the user clicks the apply coupon button")
    public void the_user_clicks_the_apply_coupon_button() {
        // Kuponu uygula / İleri butonunun xpath'ini buraya giriniz
        WebElement applyBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"post-1955\"]/div/div/form/div[1]/div[1]/table/tbody/tr[2]/td/div/button")));
        js.executeScript("arguments[0].click();", applyBtn);
        yavaslat(3000); // Kuponun sorgulanması için bekleme
    }

    @Then("g40. the user should see the coupon error message")
    public void the_user_should_see_the_coupon_error_message() {

        // Ekranda çıkması gereken hata mesajının xpath'ini buraya giriniz
        String errorXpath = "//*[@id=\"coupon-error-notice\"]";

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorXpath)));

        // Mesaj görünürse test PASSED olur
        Assert.assertTrue(errorMsg.isDisplayed(), "Geçersiz kupon hata mesajı görünmedi! Test FAILED.");
        System.out.println("Test Başarılı: Hata mesajı başarıyla görüntülendi.");


    }
}