package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    private WebDriver driver;

    // Default WooCommerce search input name is usually 's'
    @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")
    private WebElement searchInputBox;

    @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/div[2]/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"main-content\"]/div/div/div[1]/div/div")
    private WebElement noResultsMessage;

    @FindBy(name = "product_cat")
    private WebElement categoryDropdown;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Enters the given keyword into the search bar.
     */
    public void enterSearchKeyword(String keyword) {
        searchInputBox.clear();
        searchInputBox.sendKeys(keyword);
    }

    /**
     * Clicks the search submit button.
     */
    public void clickSearchButton() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", searchButton);
    }

    /**
     * Selects a specific category from the dropdown.
     */
    public void selectCategory(String categoryName) {
        // Implementation for selecting from dropdown (e.g., using Select class)
    }

    public boolean isNoResultsMessageDisplayed() {
        return noResultsMessage.isDisplayed();
    }

    // Javascript ile anında metin gönderme metodu
    public void enterTextWithJS(String text) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        // Elementin değerini anında text ile değiştirir
        js.executeScript("arguments[0].value='" + text + "';", searchInputBox);
    }
}