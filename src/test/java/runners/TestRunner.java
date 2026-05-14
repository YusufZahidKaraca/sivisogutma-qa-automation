package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        // Feature dosyalarının yolu
        features = "src/test/resources/features",

        // Step definition klasörün (Küçük-büyük harf duyarlıdır, 'stepDefinitions' ise öyle yazmalısın)
        glue = "stepDefinitions",

        // Burayı senin listendeki gruplara göre güncelledim
        // Sadece arama testleri için: "@search"
        // Sadece sepet testleri için: "@cart"
        // Hepsi için: "@search or @cart"
        tags = "@search or @cart",

        // Raporlama ve çıktı formatı
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        },

        monochrome = true,
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
        // TestNG ile Cucumber'ı bağlayan ana sınıf
}