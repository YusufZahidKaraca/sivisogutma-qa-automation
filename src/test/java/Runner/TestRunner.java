// Dosya: src/test/java/Runner/TestRunner.java
package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinition",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
                // Extent reports aktif edilecekse "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" eklenebilir.
        },
        monochrome = true,
        dryRun = false // True yaparsan tarayıcıyı açmadan sadece eksik step var mı diye kontrol eder.
)
public class TestRunner extends AbstractTestNGCucumberTests {
}