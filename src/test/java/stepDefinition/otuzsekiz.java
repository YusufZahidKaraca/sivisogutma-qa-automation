package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;
import java.time.Duration;

public class otuzsekiz {

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

    @Given("g38. the user navigates to the category page {string}")
    public void the_user_navigates_to_the_category_page(String url) {
        driver.get(url);
        yavaslat(2000); // Sayfanın temel yüklemesi
    }

    @When("g38. the user clicks on the quick view button of a product")
    public void the_user_clicks_on_the_quick_view_button_of_a_product() {

        // Sitenin ürünleri yüklemesi için sayfayı yavaşça 3 adımda aşağı kaydırıyoruz
        for(int i = 0; i < 3; i++) {
            js.executeScript("window.scrollBy(0, 300);");
            yavaslat(500);
        }

        // LÜTFEN BURAYA KISA VE RELATİF BİR XPATH GİRİN (Örn: //a[contains(@class, 'quick-view')])
        // Eski dinamik xpath: //*[@id="section-6a050f58c6e0e"]/div[2]/div/ul/div[1]/div/div[3]/li/ul/li/div/div[1]/div/div/a
        String saglamXpath = "//a[contains(@class, 'quickview') and @data-product_id='40666']";

        WebElement quickViewBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(saglamXpath)));

        // Elementi bulduktan sonra tam ortalayacak şekilde anında kaydırıyoruz
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", quickViewBtn);
        yavaslat(1000);

        // JAVASCRIPT İLE ZORLA TIKLAMA
        js.executeScript("arguments[0].click();", quickViewBtn);
    }

    @Then("g38. the quick view modal should be displayed successfully")
    public void the_quick_view_modal_should_be_displayed_successfully() {

            yavaslat(2500); // Modalın tam yüklenmesi için süreyi biraz uzattık

            // LÜTFEN BURAYA RELATIVE XPATH GİRMEYİ DENE (Örn: //div[@class='modal-content'])
            String targetXpath = "/html/body/div[2]/div/div[1]/div";

            boolean isDisplayed = false;

            try {
                // 1. AŞAMA: Element HTML kodunda var mı? (Görünür olmasa bile var mı diye bakar)
                WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(targetXpath)));

                // 2. AŞAMA: Element ekranda görünür durumda mı?
                if (targetElement.isDisplayed()) {
                    isDisplayed = true;
                    System.out.println("BAŞARILI: Element bulundu ve ekranda görünür durumda.");
                } else {
                    System.out.println("HATA: Element HTML içinde bulundu ancak ekranda GÖRÜNMEZ (hidden) durumda!");
                }

            } catch (Exception e) {
                System.out.println("KARTAL GÖZÜ HATA RAPORU: Element DOM'da (sayfa kodlarında) HİÇ BULUNAMADI!");
                System.out.println("1. XPath tamamen yanlış/değişmiş olabilir.");
                System.out.println("2. Modal bir 'iframe' içinde açılıyor olabilir.");
                isDisplayed = false;
            }

            // Testin sonucunu belirle
            Assert.assertTrue(isDisplayed, "Hedeflenen element ekranda bulunamadı veya açılmadı. Lütfen konsoldaki hata raporunu inceleyin.");


    }
}