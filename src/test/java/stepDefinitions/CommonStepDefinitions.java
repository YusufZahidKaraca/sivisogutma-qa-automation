package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class CommonStepDefinitions {

    public static WebDriver driver;

    // ==========================================================
    // HOCAYA GÖSTERİRKEN "chrome" VEYA "firefox" YAPMAN YETERLİ
    String secilenTarayici = "firefox";
    // ==========================================================

    @Before
    public void setUp() {
        if (secilenTarayici.equalsIgnoreCase("firefox")) {
            // Firefox kendi Gecko motorunu kullanır. Selenium onu GÖZÜ KAPALI bulur!
            driver = new FirefoxDriver();
        } else {
            // Varsayılan kralımız Chrome
            driver = new ChromeDriver();
        }

        // Tarayıcıyı tam ekran yap
        driver.manage().window().maximize();

        // Selenium'a elementleri bulması için 10 saniye avans ver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- BÜTÜN TESTLERDE ORTAK KULLANILACAK ADIMLAR ---

    @Given("C the user navigates to the {string} website")
    public void the_user_navigates_to_the_website(String url) {
        driver.get(url);

        // ZOOM KODU BURADA OLMALI! Site açıldıktan sonra %75 küçültüyoruz.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='75%'");
    }



    @Given("C the user is logged into the {string} system")
    public void the_user_is_logged_into_the_system(String url) {
        // 1. Siteye git
        driver.get(url);

        // 2. ZOOM KODU: Çerez pop-up'ı giriş butonunu engellemesin diye %75 küçültüyoruz
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='75%'");

        // SADECE GİRİŞ YAP (LOGIN) KISMININ STANDART ID'LERİ
        WebElement emailKutusu = driver.findElement(By.id("username"));
        WebElement sifreKutusu = driver.findElement(By.id("password"));

        // 3. JavaScript ile kutuları kesin olarak dolduruyoruz
        String gercekMail = "ahmet";
        String gercekSifre = "SCnUhFiEyi3D3Yv";

        // JavaScript ile değerleri direkt HTML'in içine zorla yazdırıyoruz
        js.executeScript("arguments[0].value='" + gercekMail + "';", emailKutusu);
        js.executeScript("arguments[0].value='" + gercekSifre + "';", sifreKutusu);

        // Sitenin değişimi algılaması için çok kısa bir es
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // 4. Giriş butonuna tıkla
        driver.findElement(By.name("login")).click();
    }


}