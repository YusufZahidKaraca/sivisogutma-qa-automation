package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;
import java.time.Duration;

public class otuzaltı {

    WebDriver driver = Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Kodu yavaşlatmak için kullanılacak yardımcı metot
    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g36. the user navigates to the specific product page {string}")
    public void the_user_navigates_to_the_specific_product_page(String url) {
        driver.get(url);
        yavaslat(2000); // Sayfanın temel olarak yüklenmesi için bekleme
    }

    @Then("g36. the product description should be loaded and contain at least {int} characters")
    public void the_product_description_should_be_loaded_and_contain_at_least_characters(Integer minLength) {

            // Ürün açıklamasının bulunduğu HTML elementinin (p, div veya span) xpath'ini buraya giriniz
            WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tab-description\"]/div")));

            // Elementin içindeki metni çekiyoruz ve etrafındaki gereksiz boşlukları siliyoruz
            String descriptionText = descriptionElement.getText().trim();
            int actualLength = descriptionText.length();

            // Uzunluğun 150 karakter veya daha fazla olup olmadığını boolean bir değişkene atıyoruz
            boolean isLengthValid = actualLength >= minLength;

            // Eğer isLengthValid "true" ise test Pass olur. "false" ise alttaki mesajı verip Fail olur.
            Assert.assertTrue(isLengthValid, "The description length is less than 150 characters or not loaded properly. Actual length: " + actualLength + ". Character limit test FAILED.");


    }
}