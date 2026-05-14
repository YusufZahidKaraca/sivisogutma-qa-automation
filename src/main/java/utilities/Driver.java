package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class Driver {

    // ThreadLocal, TestNG'de her senaryo için tamamen bağımsız ve temiz bir WebDriver havuzu oluşturur.
    // Bu yapı "Session ID is null" hatasının en kesin çözümüdür.
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {
        // Singleton pattern için constructor gizlendi
    }

    public static WebDriver getDriver() {
        // İlgili thread'de (testte) aktif bir driver yoksa yeni bir tane oluşturur
        if (driverPool.get() == null) {

            // ÇALIŞTIRMAK İSTEDİĞİN TARAYICIYI BURADAN SEÇEBİLİRSİN: "chrome" veya "firefox"
            // İleride bu değeri bir configuration.properties dosyasından çekerek kodu hiç açmadan tarayıcı değiştirebilirsin.
            String browser = "firefox";

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    // Firefox'ta bildirimleri kapatmak için Chrome'dan farklı bir ayar kullanılır
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);

                    WebDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
                    driverPool.set(firefoxDriver);
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--start-maximized");

                    WebDriver chromeDriver = new ChromeDriver(chromeOptions);
                    driverPool.set(chromeDriver);
                    break;
            }

            // Seçilen tarayıcı hangisi olursa olsun ortak ayarlar (bekleme süresi ve pencere büyütme) burada uygulanır
            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driverPool.get().manage().window().maximize();
        }

        // Eğer o thread'de zaten bir driver varsa, olanı geri döndürür
        return driverPool.get();
    }

    public static void closeDriver() {
        // İlgili thread'deki driver boş değilse kapatma işlemini yapar
        if (driverPool.get() != null) {
            driverPool.get().quit(); // Tarayıcıyı tamamen öldürür
            driverPool.remove(); // Thread hafızasını tamamen temizler
        }
    }
}