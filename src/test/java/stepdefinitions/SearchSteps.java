package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SearchPage;
import utils.ConfigReader;
import utils.Driver;

public class SearchSteps {

    SearchPage searchPage = new SearchPage(Driver.getDriver());

    @Given("user navigates to the homepage")
    public void user_navigates_to_the_homepage() {
        // URL'i de properties dosyasından alıyoruz ki kod temiz kalsın
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @When("user searches for a valid product {string}")
    public void user_searches_for_a_valid_product(String keyword) {
        searchPage.enterSearchKeyword(keyword);
        searchPage.clickSearchButton();
    }

    @Then("user should see relevant search results")
    public void user_should_see_relevant_search_results() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertTrue("Search results are not displayed!", Driver.getDriver().getCurrentUrl().contains("?s="));
    }

    @When("user searches for an invalid product {string}")
    public void user_searches_for_an_invalid_product(String invalidKeyword) {
        searchPage.enterSearchKeyword(invalidKeyword);
        searchPage.clickSearchButton();
    }

    @Then("user should see a no results found message")
    public void user_should_see_a_no_results_found_message() {
        Assert.assertTrue("No results message NOT displayed!", searchPage.isNoResultsMessageDisplayed());
    }

    @When("user inputs a string of {int} characters into the search bar")
    public void user_inputs_a_string_of_characters_into_the_search_bar(Integer length) {
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            longString.append("a");
        }
        // sendKeys kullanan metod yerine yeni yazdığımız JS metodunu çağırıyoruz
        searchPage.enterTextWithJS(longString.toString());
    }
    @When("clicks the search button")
    public void clicks_the_search_button() {
        searchPage.clickSearchButton();
    }

    @Then("the system should handle the input without crashing")
    public void the_system_should_handle_the_input_without_crashing() {
        // Eğer başlığı alabiliyorsak tarayıcı çökmemiş demektir
        Assert.assertNotNull("System crashed during extreme input!", Driver.getDriver().getTitle());
    }

    @When("user searches using a unicode space {string}")
    public void user_searches_using_a_unicode_space(String unicodeSpace) {
        searchPage.enterSearchKeyword(unicodeSpace);
        searchPage.clickSearchButton();
    }

    @Then("the system should process the unicode search appropriately")
    public void the_system_should_process_the_unicode_search_appropriately() {
        // Unicode aramada sitenin verdiği tepkiye göre buradaki assert güncellenebilir
        Assert.assertNotNull(Driver.getDriver().getTitle());
    }

    @When("user searches using a normal space {string}")
    public void user_searches_using_a_normal_space(String normalSpace) {
        searchPage.enterSearchKeyword(normalSpace);
        searchPage.clickSearchButton();
    }

    @Then("user should remain on the current page or see default results")
    public void user_should_remain_on_the_current_page_or_see_default_results() {
        Assert.assertNotNull(Driver.getDriver().getTitle());
    }

    @When("user selects a category")
    public void user_selects_a_category() {
        searchPage.selectCategory("Aksesuar"); // Kategori ismini buraya yazdık
    }

    @When("performs an empty search")
    public void performs_an_empty_search() {
        searchPage.enterSearchKeyword("");
        searchPage.clickSearchButton();
    }

    @Then("the system should display all products in that category")
    public void the_system_should_display_all_products_in_that_category() throws InterruptedException {
        Thread.sleep(2000);
        // Kategori aramasının başarılı olduğunu anlamak için url veya sayfa içi bir kontrol
        Assert.assertTrue("Category results not shown!", Driver.getDriver().getCurrentUrl().contains("cat"));
    }
}