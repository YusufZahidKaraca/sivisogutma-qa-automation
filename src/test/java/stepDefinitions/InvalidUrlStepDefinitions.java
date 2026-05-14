package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class InvalidUrlStepDefinitions {

    @Then("46. it is verified that a 404 page not found error is displayed")
    public void it_is_verified_that_a_404_page_not_found_error_is_displayed() {
        // BURAYA SİTEDE OLMAYAN BİR SAYFAYA GİDİLDİĞİNDE ÇIKAN '404', 'Sayfa Bulunamadı' GİBİ HATA MESAJININ GERÇEK XPATH'İNİ YAZMALISIN
        WebElement error404Message = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/div/div/div"));
        Assert.assertTrue(error404Message.isDisplayed(), "Beklenen 404 Hata sayfası görünmedi!");
    }
}