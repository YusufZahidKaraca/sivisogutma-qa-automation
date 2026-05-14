package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilities.Driver;
import java.time.Duration;

public class kırkbes {

    WebDriver driver = Driver.getDriver();
    long startTime;
    long endTime;
    long duration;

    // Kodu yavaşlatmak için kullanılan yardımcı metot
    private void yavaslat(int milisaniye) {
        try {
            Thread.sleep(milisaniye);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("g45. the user measures the load time of the page {string}")
    public void the_user_measures_the_load_time_of_the_page(String url) {
        // Yükleme başlamadan hemen önceki zamanı alıyoruz
        startTime = System.currentTimeMillis();

        driver.get(url);

        // driver.get() metodu sayfa yüklenene kadar bekler. Bittiği an end zamanını alıyoruz.
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Page Load Time: " + duration + " milliseconds");
    }

    @Then("g45. the total load time should be under {int} milliseconds")
    public void the_total_load_time_should_be_under_milliseconds(Integer maxTime) {

            // Ölçülen süre belirtilen sınırdan (5000ms) küçükse test PASSED olur
            boolean isFastEnough = duration < maxTime;

            Assert.assertTrue(isFastEnough, "Page load took too long! Duration: " + duration + " ms. Test FAILED.");
            System.out.println("Test PASSED: Page loaded in " + duration + " ms.");


    }
}