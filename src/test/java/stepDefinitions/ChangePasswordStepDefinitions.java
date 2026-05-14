package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ChangePasswordStepDefinitions {

    // =====================================================================
    // KONTROL PANELİ: Sadece bu rakamı 1 veya 0 yapman yeterli!
    // 1 yaparsan: V8be9... şifresiyle girer, 7$tRq... yapar.
    // 0 yaparsan: 7$tRq... şifresiyle girer, V8be9... yapar.
    // =====================================================================
    int sifreModu = 1;

    // Şifreleri sadece bir kez buraya tanımlıyoruz
    String sifreA = "V8be9YVLtmMeP6T";
    String sifreB = "7$tRq@nB1&uW9";

    // Java'nın sihirli If-Else kısaltması:
    // "sifreModu 1 mi? Evetse mevcutSifre sifreA olsun, Hayırsa sifreB olsun."
    String mevcutSifrem = (sifreModu == 1) ? sifreA : sifreB;
    String yeniSifrem = (sifreModu == 1) ? sifreB : sifreA;

    // =====================================================================
    // SADECE BU TESTE ÖZEL İZOLE GİRİŞ ADIMI
    // =====================================================================
    @Given("29. the user logs in with a special account for password change at {string}")
    public void the_user_logs_in_with_a_special_account_for_password_change_at(String url) {
        CommonStepDefinitions.driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) CommonStepDefinitions.driver;
        js.executeScript("document.body.style.zoom='75%'");

        WebElement emailKutusu = CommonStepDefinitions.driver.findElement(By.id("username"));
        WebElement sifreKutusu = CommonStepDefinitions.driver.findElement(By.id("password"));

        // 1. KATMAN: Klasik Selenium Temizliği
        emailKutusu.clear();
        sifreKutusu.clear();

        // 2. KATMAN: JavaScript ile "Sıfırın Altına" İniş
        // Firefox bazen clear() komutunu görmezden gelir, bu satır kesin çözüm.
        js.executeScript("arguments[0].value = '';", emailKutusu);
        js.executeScript("arguments[0].value = '';", sifreKutusu);

        String ozelTestMaili = "muhammed";

        // 3. KATMAN: Veriyi Basma
        // SendKeys yerine direkt value atıyoruz ki Firefox'un autofill'i devreye giremesin.
        js.executeScript("arguments[0].value='" + ozelTestMaili + "';", emailKutusu);
        js.executeScript("arguments[0].value='" + mevcutSifrem + "';", sifreKutusu);

        try { Thread.sleep(800); } catch (InterruptedException e) {} // Es süresini azıcık artırdık

        CommonStepDefinitions.driver.findElement(By.name("login")).click();
    }

    @When("29. the user goes to the account settings page")
    public void the_user_goes_to_the_account_settings_page() {
        WebElement settingsLink = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/nav/ul/li[5]/a"));
        settingsLink.click();
    }

    @When("29. the user enters the current password and a new password")
    public void the_user_enters_the_current_password_and_a_new_password() {
        WebElement currentPasswordInput = CommonStepDefinitions.driver.findElement(By.id("password_current"));
        WebElement newPasswordInput = CommonStepDefinitions.driver.findElement(By.id("password_1"));
        WebElement confirmNewPasswordInput = CommonStepDefinitions.driver.findElement(By.id("password_2"));

        // Kod tertemiz oldu. Hangi moddaysak kutular ona göre dolacak.
        currentPasswordInput.sendKeys(mevcutSifrem);
        newPasswordInput.sendKeys(yeniSifrem);
        confirmNewPasswordInput.sendKeys(yeniSifrem);
    }

    @When("29. the user clicks the save changes button")
    public void the_user_clicks_the_save_changes_button() {
        WebElement saveButton = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div/form/p[5]/button"));

        // saveButton.click(); // Çerez çubuğuna takılan eski kodumuz buydu.

        // JAVASCRIPT FORCE CLICK: Çerez çubuğunu delip geçen kesin tıklama yöntemi
        JavascriptExecutor js = (JavascriptExecutor) CommonStepDefinitions.driver;
        js.executeScript("arguments[0].click();", saveButton);
    }

    @Then("29. it is verified that a password changed successfully message is displayed")
    public void it_is_verified_that_a_password_changed_successfully_message_is_displayed() {
        WebElement successMessage = CommonStepDefinitions.driver.findElement(By.cssSelector(".woocommerce-message"));
        Assert.assertTrue(successMessage.isDisplayed(), "Şifre değiştirme başarılı mesajı ekranda görünmedi!");
    }
}