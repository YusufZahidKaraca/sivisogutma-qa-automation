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

public class otuzyedi {

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

    @Given("g37. the user navigates to the product page {string}")
    public void the_user_navigates_to_the_product_page(String url) {
        driver.get(url);
        yavaslat(2000); // Sayfanın tamamen yüklenmesi için temel bekleme
    }

    @When("g37. the user clicks on the brand link")
    public void the_user_clicks_on_the_brand_link() {
        // Marka tuşunun (linkinin/butonunun) xpath'ini buraya giriniz
        WebElement brandLinkBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[5]/span[2]/a")));

        // Elementin tıklanabilir hale gelmesi için ekranda o bölgeye kaydırıyoruz (Scroll)
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", brandLinkBtn);
        yavaslat(1000); // Kaydırma animasyonunun bitmesi için bekleme

        // Önce standart tıklama denenir, arayüzde bir engel varsa JavaScript tıklamasına geçilir
        try {
            brandLinkBtn.click();
        } catch (Exception e) {
            System.out.println("Normal tıklama başarısız oldu, JavaScript Executor ile marka tuşuna tıklanıyor...");
            js.executeScript("arguments[0].click();", brandLinkBtn);
        }
    }

    @Then("g37. the user should see the specific brand element on the brand page")
    public void the_user_should_see_the_specific_brand_element_on_the_brand_page() {

            yavaslat(3000); // Yeni açılan marka sayfasının tam olarak yüklenmesi için bekleme

            // Marka sayfasında, yönlendirmenin başarılı olduğunu kanıtlayacak elementin (Örn: Marka Logosu, Başlık) xpath'ini buraya giriniz
            String brandPageTargetXpath = "//*[@id=\"header\"]/div[4]/div/div/div/ul/li[3]";

            // Hedef elementin sayfada görünür olup olmadığını kontrol ediyoruz
            WebElement brandPageTargetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(brandPageTargetXpath)));

            // Eğer hedef element sayfada başarılı bir şekilde bulunup görüntüleniyorsa test Pass olur, aksi halde Fail mesajı verir.
            Assert.assertTrue(brandPageTargetElement.isDisplayed(), "The expected element on the brand page was not found. Redirection test FAILED.");


    }
}