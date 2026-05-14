package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;
import java.time.Duration;

public class otuzdokuz {

    WebDriver driver = Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // İlk sekmenin kimliğini tutacağımız değişken
    String originalTab;

    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g39. the user navigates to the first product page {string}")
    public void the_user_navigates_to_the_first_product_page(String url) {
        driver.get(url);
        yavaslat(2000);
    }

    @Given("g39. the user clicks the add to cart button for the first product")
    public void the_user_clicks_the_add_to_cart_button_for_the_first_product() {
        // Birinci ürünün sepete ekle butonu xpath'ini buraya giriniz
        WebElement firstAddBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstAddBtn);
        yavaslat(1000);
        js.executeScript("arguments[0].click();", firstAddBtn);
        yavaslat(2000);
    }

    @When("g39. the user navigates to the checkout page {string}")
    public void the_user_navigates_to_the_checkout_page(String url) {
        driver.get(url);
        yavaslat(2000);

        // Ödeme sayfasına (ilk sekmeye) geldiğimizde, bu sekmenin kimliğini (ID) hafızaya kaydediyoruz
        originalTab = driver.getWindowHandle();
    }

    @When("g39. the user opens a new tab and navigates to the second product page {string}")
    public void the_user_opens_a_new_tab_and_navigates_to_the_second_product_page(String url) {
        // Tarayıcıda yepyeni bir sekme açıyoruz ve Selenium'un kontrolünü o sekmeye geçiriyoruz
        driver.switchTo().newWindow(WindowType.TAB);

        // Yeni sekmede ikinci ürünün URL'sine gidiyoruz
        driver.get(url);
        yavaslat(2000);
    }

    @When("g39. the user clicks the add to cart button for the second product")
    public void the_user_clicks_the_add_to_cart_button_for_the_second_product() {
        // İkinci ürünün sepete ekle butonu xpath'ini buraya giriniz
        WebElement secondAddBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", secondAddBtn);
        yavaslat(1000);
        js.executeScript("arguments[0].click();", secondAddBtn);
        yavaslat(3000); // Sepete tam eklendiğinden emin olmak için biraz daha bekliyoruz
    }

    @When("g39. the user switches back to the checkout tab and refreshes the page")
    public void the_user_switches_back_to_the_checkout_tab_and_refreshes_the_page() {
        // Hafızaya aldığımız ilk sekmenin kimliğini kullanarak o sekmeye geri dönüyoruz
        driver.switchTo().window(originalTab);
        yavaslat(1000);

        // Ödeme sayfasını yeniliyoruz (Refresh)
        driver.navigate().refresh();
        yavaslat(3000); // Yenileme sonrası sayfanın yüklenmesini bekle
    }

    @Then("g39. the user should see the second product in the checkout list successfully")
    public void the_user_should_see_the_second_product_in_the_checkout_list_successfully() {
        // Sayfa yenilendikten sonra, İKİNCİ ÜRÜNÜN ödeme listesine geldiğini kanıtlayan xpath'i buraya giriniz
        String validationXpath = "//*[@id=\"order_review\"]/table/tbody/tr[2]/td[1]/div[2]";

        WebElement targetProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(validationXpath)));

        Assert.assertTrue(targetProduct.isDisplayed(), "Sayfa yenilendi ancak ikinci ürün ödeme ekranına YANSIMADI! Test FAILED.");
        System.out.println("Test PASSED: Yeni sekmede eklenen ürün, ödeme ekranı yenilenince başarıyla listelendi.");
    }
}