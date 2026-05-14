package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class ProductImageZoomStepDefinitions {

    @When("35. the user clicks on a product image")
    public void the_user_clicks_on_a_product_image() {
        // 1. Ana sayfadan ürün bulma kodları SİLİNDİ!
        // Çünkü Gherkin dosyasındaki Given adımıyla zaten direkt ürünün kalbine indik.

        // 2. DOĞRUDAN BÜYÜTEÇ BUTONUNA TIKLA
        WebDriverWait wait = new WebDriverWait(CommonStepDefinitions.driver, Duration.ofSeconds(10));
        WebElement zoomTrigger = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-images-wrapper\"]/div/div[2]/a")));
        zoomTrigger.click();
    }

    @Then("35. it is verified that the enlarged image modal is displayed")
    public void it_is_verified_that_the_enlarged_image_modal_is_displayed() {
        // 3. PSWP Class'ı yerine doğrudan açılan BÜYÜK RESMİ kontrol ediyoruz.
        WebDriverWait wait = new WebDriverWait(CommonStepDefinitions.driver, Duration.ofSeconds(10));

        // =====================================================================
        // DİKKAT: BURADAKİ XPATH İÇİNE KENDİ ALDIĞIN O YENİ RESMİN XPATH'İNİ YAPIŞTIR!
        WebElement enlargedImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"photoswipe-fullscreen-dialog\"]/div[2]/div[1]/div[2]/div/img")));
        // =====================================================================

        Assert.assertTrue(enlargedImage.isDisplayed(), "Ürün resmi büyütülemedi (Büyük resim ekranda bulunamadı)!");
    }
}