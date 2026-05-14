package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import java.util.ArrayList;

public class MultiTabSessionStepDefinitions {

    @When("30. the user opens a new tab and navigates to the same website")
    public void the_user_opens_a_new_tab_and_navigates_to_the_same_website() {
        // Yeni bir sekme açar ve o sekmeye odaklanır
        CommonStepDefinitions.driver.switchTo().newWindow(WindowType.TAB);
        // Zaman kazanmak için direkt hesabım sayfasına gidiyoruz
        CommonStepDefinitions.driver.get("https://sivisogutma.com/hesabim"); // URL'nin /hesabim olduğundan emin ol
    }

    @When("30. the user logs out from the new tab")
    public void the_user_logs_out_from_the_new_tab() {
        // WOOCOMMERCE TÜRKÇE ÇIKIŞ YAPMA LİNKİ
        WebElement logoutButton = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/nav/ul/li[6]/a"));
        logoutButton.click();
    }

    @When("30. the user switches back to the first tab")
    public void the_user_switches_back_to_the_first_tab() {
        // Açık olan tüm sekmelerin listesini alır ve 0. index'e (ilk sekmeye) geri döner
        ArrayList<String> tabs = new ArrayList<>(CommonStepDefinitions.driver.getWindowHandles());
        CommonStepDefinitions.driver.switchTo().window(tabs.get(0));
    }

    @When("30. the user attempts to change the password in the first tab")
    public void the_user_attempts_to_change_the_password_in_the_first_tab() {
        try {
            // WOOCOMMERCE TÜRKÇE HESAP DETAYLARI (ŞİFRE DEĞİŞTİRME BURADADIR)
            WebElement settingsLink = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/nav/ul/li[5]/a"));
            settingsLink.click();

            // WOOCOMMERCE STANDART ŞİFRE DEĞİŞTİRME ID'LERİ
            CommonStepDefinitions.driver.findElement(By.id("password_current")).sendKeys("7$tRq@nB1&uW9");
            CommonStepDefinitions.driver.findElement(By.id("password_1")).sendKeys("P1$gV5*wY2_jN");
            CommonStepDefinitions.driver.findElement(By.id("password_2")).sendKeys("P1$gV5*wY2_jN");

            // KAYDET BUTONU
            CommonStepDefinitions.driver.findElement(By.name("save_account_details")).click();
        } catch (Exception e) {
            // Eğer element bulunamazsa (sistem zaten ilk tıkta dışarı attıysa) test çökmesin diye hatayı yutuyoruz
            System.out.println("Sistem güvenliği devrede: İlk sekmede işlem yapılmak istendiğinde oturumun kapandığı anlaşıldı.");
        }
    }

    @Then("30. it is verified that the system requests a login or denies the action")
    public void it_is_verified_that_the_system_requests_a_login_or_denies_the_action() {
        // Çıkış yapıldığı için sistemin giriş formunu (username kutusu) göstermesi gerekir
        WebElement loginUsernameBox = CommonStepDefinitions.driver.findElement(By.id("username"));
        Assert.assertTrue(loginUsernameBox.isDisplayed(), "Güvenlik açığı! Sistem diğer sekmedeki çıkışı algılamadı!");
    }
}