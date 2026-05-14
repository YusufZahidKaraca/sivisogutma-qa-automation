package stepDefinition;

import io.cucumber.datatable.DataTable;
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
import java.util.List;

public class yirmiiki {

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

    @Given("g22. the user adds the following products to favorites")
    public void the_user_adds_the_following_products_to_favorites(DataTable dataTable) {
        // Cucumber Data Table'ı List'e dönüştürüyoruz
        List<String> productUrls = dataTable.asList();

        for (String url : productUrls) {
            driver.get(url);
            yavaslat(1000); // Her sayfanın temel yüklemesi için kısa bir bekleme

            // Önceki testten gelen Favorilere Ekle butonunun XPath'i kullanılıyor
            WebElement addToFavoritesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a/span")));

            // JavaScript ile Favorilere Ekle butonuna tıkla
            js.executeScript("arguments[0].click();", addToFavoritesBtn);

            // Site arka planında işlemin işlenmesi için kısa bekleme (StaleElement vs. hatalarını engeller)
            yavaslat(500);
        }
    }

    @When("g22. the user navigates to the favorites page after adding all items")
    public void the_user_navigates_to_the_favorites_page_after_adding_all_items() {
        // 53. ürün eklendikten sonra sayfanın (ve butonun) tepki vermesi için gereken ilk mola
        yavaslat(2000);

        // Elementin sadece var olmasını değil, tamamen tıklanabilir hazır duruma gelmesini bekliyoruz
        WebElement goToFavoritesBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a")));

        // Elementi garanti olması adına sayfanın ortasına kaydırıyoruz (Scroll)
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", goToFavoritesBtn);
        yavaslat(1000); // Kaydırma animasyonunun bitmesini bekle

        // Önce standart tıklama yapmayı dene, eğer bir engel (Exception) çıkarsa JavaScript ile zorla tıkla
        try {
            goToFavoritesBtn.click();
        } catch (Exception e) {
            System.out.println("Normal tıklama başarısız oldu, JavaScript Executor ile deneniyor...");
            js.executeScript("arguments[0].click();", goToFavoritesBtn);
        }

        yavaslat(2000); // Favoriler sayfasının tamamen yüklenmesi için güvenli bekleme süresi
    }

    @Then("g22. the user should see the specific target element on the favorites page")
    public void the_user_should_see_the_specific_target_element_on_the_favorites_page() {

            // Favoriler sayfasında görmeyi beklediğin elementin XPath'ini buraya giriniz
            // Örnek: Favorilerdeki toplam ürün sayısını gösteren bir metin veya en son eklenen ürün
            String targetXpath = "//*[@id=\"header\"]/div[4]/div/div/div/ul/li[2]";

            // Elementin DOM'da bulunup bulunmadığını kontrol ediyoruz
            WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(targetXpath)));

            // Eğer element bulunduysa (görünürse) true dönecek, test Passed olacak. Bulunamazsa TimeOutException fırlatıp Fail olacak.
            Assert.assertTrue(targetElement.isDisplayed(), "The target element was not found on the favorites page. Test FAILED.");


    }
}