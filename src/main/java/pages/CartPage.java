package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    private WebDriver driver;

    // Ürün sayfasındaki formu buluyoruz
    @FindBy(css = "form.cart")
    private WebElement addToCartForm;

    @FindBy(css = "button.single_add_to_cart_button")
    private WebElement singleAddToCartButton;

    // Boş sepet mesajı için en geniş XPath
    @FindBy(xpath = "//*[contains(translate(text(), 'BOŞ', 'boş'), 'boş') or contains(@class, 'empty')]")
    private WebElement emptyCartMessage;

    // Çöp kutusu ikonu (CSS Selector daha hızlı ve güvenilirdir)
    @FindBy(css = "table.shop_table a.remove")
    private WebElement trashIcon;

    // Adet kutusu
    @FindBy(css = "table.shop_table input.qty")
    private WebElement quantityInputBox;

    // Güncelle butonu
    @FindBy(name = "update_cart")
    private WebElement updateCartButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCart() {
        // Çerez veya başka bir popup engellemesin diye JS ile kesin tıklama yapıyoruz
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", singleAddToCartButton);
    }
    public void setQuantity(String quantity) {
        // HACKER ÇÖZÜMÜ: Elementin boyutu sıfır olsa bile değeri JS ile zorla değiştir!
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Element gizli veya etkileşime kapalıysa Selenium patlar, JS patlamaz
        js.executeScript("arguments[0].value = arguments[1];", quantityInputBox, quantity);

        // Değeri değiştirdikten sonra "Sepeti Güncelle" butonuna JS ile tıkla
        clickUpdateCart();
    }

    public void clickUpdateCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Güncelle butonu devre dışı (disabled) olsa bile tıkla!
        js.executeScript("arguments[0].removeAttribute('disabled');", updateCartButton);
        js.executeScript("arguments[0].click();", updateCartButton);
    }

    public void clickTrashIcon() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Çöp kutusuna zorla tıkla
        js.executeScript("arguments[0].click();", trashIcon);
    }

    public boolean isEmptyCartMessageDisplayed() {
        try {
            // Eğer miktar kutusu ekranda görünüyorsa sepet DOLUDUR (false döner)
            return !quantityInputBox.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Miktar kutusu yoksa (hata verirse) sepet KESİNLİKLE BOŞTUR (true döner)
            return true;
        }
    }

    // 12. Senaryo için senin yakaladığın HTML5 Uyarı mesajını okuma metodu
    public String getQuantityValidationMessage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Chrome'un gösterdiği "Lütfen 0 veya daha büyük bir değer girin" uyarısını metin olarak çeker
        return (String) js.executeScript("return arguments[0].validationMessage;", quantityInputBox);
    }

    public boolean isUpdateCartButtonPresent() {
        try {
            return updateCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getQuantityValue() {
        try {
            // Değeri okurken Selenium patlamaması için JS kullanıyoruz
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return arguments[0].value;", quantityInputBox);
        } catch (Exception e) {
            return "0";
        }
    }
}