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

public class yirmibir {

    // WebDriver ve WebDriverWait tanımlamaları
    WebDriver driver = Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Arayüz engellerini aşmak için JavascriptExecutor tanımlaması
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Kodu yavaşlatmak için kullanılacak yardımcı metot
    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g21. the user navigates to the product page {string}")
    public void the_user_navigates_to_the_product_page(String url) {
        driver.get(url);
        yavaslat(2000); // Sayfa yüklendikten sonra 2 saniye bekle
    }

    @When("g21. the user clicks on the add to favorites button")
    public void the_user_clicks_on_the_add_to_favorites_button() {
        WebElement addToFavoritesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a/span")));
        yavaslat(1000);
        js.executeScript("arguments[0].click();", addToFavoritesBtn);
    }

    @When("g21. the user clicks on the go to favorites button")
    public void the_user_clicks_on_the_go_to_favorites_button() {
        // StaleElementReferenceException hatasını önlemek için elementi BULMADAN ÖNCE bekliyoruz.
        // Böylece favorilere ekleme işleminden sonra sayfanın (DOM'un) yenilenmesine zaman tanıyoruz.
        yavaslat(2000);

        WebElement goToFavoritesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a")));
        yavaslat(1000);
        js.executeScript("arguments[0].click();", goToFavoritesBtn);
    }

    @When("g21. the user clicks on the favorites search bar")
    public void the_user_clicks_on_the_favorites_search_bar() {
        yavaslat(2000); // Sayfa geçişinin tamamlanmasını bekle
        WebElement searchBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"search-2\"]/form/label/input")));
        yavaslat(1000);
        js.executeScript("arguments[0].click();", searchBar);
    }

    @When("g21. the user tries to enter a {int} character long text into the search bar")
    public void the_user_tries_to_enter_a_character_long_text_into_the_search_bar(Integer length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append("A");
        }
        String testString = stringBuilder.toString();

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-2\"]/form/label/input")));
        yavaslat(1000);
        searchBar.sendKeys(testString);
        yavaslat(2000); // 1000 karakter girildikten sonra son durumu görmek için 2 saniye bekle
    }

    @Then("g21. the search bar should not accept the full {int} characters")
    public void the_search_bar_should_not_accept_the_full_characters(Integer expectedLength) {
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-2\"]/form/label/input")));

        String enteredText = searchBar.getAttribute("value");
        int actualLength = enteredText.length();


            // Eğer arama çubuğuna girilen karakter sayısı 1000'den küçükse test PASSED olur.
            Assert.assertTrue(actualLength < expectedLength, "The search bar accepted all 1000 characters. Character limit test FAILED.");

    }
}