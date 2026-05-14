package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.CartPage;
import utils.ConfigReader;
import utils.Driver;

public class CartSteps {

    CartPage cartPage = new CartPage(Driver.getDriver());
    String inStockProductUrl = "https://sivisogutma.com/urun/bitspower-g14-matt-black-premium-quick-13-19mm-hortum-rakoru/";

    @When("user navigates to the cart page directly")
    public void user_navigates_to_the_cart_page_directly() {
        // Sadece bu senaryoda (Sepet Boş mu testi) çerezleri silmek mantıklıdır
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/cart");
    }

    @Then("user should see an empty cart message")
    public void user_should_see_an_empty_cart_message() {
        Assert.assertTrue("Empty cart message is missing!", cartPage.isEmptyCartMessageDisplayed());
    }

    @When("clicks add to cart")
    public void clicks_add_to_cart() throws InterruptedException {
        String currentUrl = Driver.getDriver().getCurrentUrl();

        // Eğer linkte "cart" kelimesi varsa sepet sayfasındayız demektir, güncelle butonuna basar
        if(currentUrl.contains("cart")) {
            cartPage.clickUpdateCart();
            Thread.sleep(2000);
        } else {
            // Değilse ürün sayfasındayızdır, sepete ekle butonuna basar
            cartPage.clickAddToCart();
        }
    }

    @Given("user has a product in the cart")
    public void user_has_a_product_in_the_cart() throws InterruptedException {
        // 1. Testin başında çerezleri sıfırla ki eski sepetler kafamızı karıştırmasın
        Driver.getDriver().manage().deleteAllCookies();

        // 2. Doğrudan stokta olan ürünün sayfasına git
        Driver.getDriver().get(inStockProductUrl);
        Thread.sleep(2000); // Sayfanın ve butonun yüklenmesini bekle

        // 3. İŞTE SENİN TESPİTİN: O sayfadaki "Sepete Ekle" butonuna bas!
        cartPage.clickAddToCart();
        Thread.sleep(3000); // WooCommerce'in ürünü sepete atması için zaman tanı (AJAX)

        // 4. Artık ürün sepette, sepet sayfasına gidebiliriz
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/cart");
    }

    @Given("user has a product in the cart with quantity {int}")
    public void user_has_a_product_in_the_cart_with_quantity(Integer qty) throws InterruptedException {
        user_has_a_product_in_the_cart(); // Temiz sepete 1 tane eklendi ve sepete gidildi

        cartPage.setQuantity(String.valueOf(qty)); // Miktar güncellendi
        Thread.sleep(3000); // Sistemin fiyatları hesaplamasını bekle
        Driver.getDriver().navigate().refresh();
    }

    @When("user clicks add to cart for a product")
    public void user_clicks_add_to_cart_for_a_product() throws InterruptedException {
        Driver.getDriver().manage().deleteAllCookies(); // Temiz bir test için
        Driver.getDriver().get(inStockProductUrl);
        cartPage.clickAddToCart();
        Thread.sleep(2000);
    }

    @When("user clicks add to cart for the same product again")
    public void user_clicks_add_to_cart_for_the_same_product_again() throws InterruptedException {
        // Aynı ürünü ürün sayfasından 2. kez ekliyoruz (Senaryo 9'un orijinal amacına sadık kalarak)
        Driver.getDriver().get(inStockProductUrl);
        cartPage.clickAddToCart();
        Thread.sleep(2000);
    }

    @Then("the product should be added to the cart successfully")
    public void the_product_should_be_added_to_the_cart_successfully() {
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/cart");
        Assert.assertFalse("Cart is still empty!", cartPage.isEmptyCartMessageDisplayed());
    }

    @Then("the product quantity should be updated in the cart")
    public void the_product_quantity_should_be_updated_in_the_cart() {
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/cart");
        String qty = cartPage.getQuantityValue();
        Assert.assertEquals("Quantity should be 2", "2", qty);
    }

    @When("user decreases the product quantity by {int}")
    public void user_decreases_the_product_quantity_by(Integer amount) throws InterruptedException {
        int currentQty = Integer.parseInt(cartPage.getQuantityValue());
        int newQty = currentQty - amount;
        cartPage.setQuantity(String.valueOf(newQty)); // Bu metod aynı zamanda Güncelle butonuna basar
        Thread.sleep(3000);
        Driver.getDriver().navigate().refresh();
    }

    @Then("the cart should display the product with quantity {int}")
    public void the_cart_should_display_the_product_with_quantity(Integer expectedQty) {
        String qty = cartPage.getQuantityValue();
        Assert.assertEquals("Quantity did not match!", String.valueOf(expectedQty), qty);
    }

    @When("user clicks the trash icon for the product")
    public void user_clicks_the_trash_icon_for_the_product() throws InterruptedException {
        cartPage.clickTrashIcon();
        Thread.sleep(3000);
    }

    @Then("the product should be removed from the cart")
    public void the_product_should_be_removed_from_the_cart() throws InterruptedException {
        Driver.getDriver().navigate().refresh();
        Assert.assertTrue("Product was not deleted!", cartPage.isEmptyCartMessageDisplayed());
    }

    @When("user attempts to input {string} as product quantity")
    public void user_attempts_to_input_as_product_quantity(String negativeValue) throws InterruptedException {
        user_has_a_product_in_the_cart(); // Sepete ürünü atıp sepet sayfasına geldik
        cartPage.setQuantity(negativeValue);
        Thread.sleep(2000);
    }

    @Then("the system should prevent adding negative quantities")
    public void the_system_should_prevent_adding_negative_quantities() {
        // Çıkan o uyarı mesajını sayfadan okuyoruz
        String validationMsg = cartPage.getQuantityValidationMessage();

        // Eğer mesaj boş değilse (yani ekranda "value must be greater..." yazdıysa),
        // sistem eksi girmeyi başarıyla engellemiş demektir!
        Assert.assertTrue("Sistem negatif değere izin verdi, uyarı vermedi!",
                validationMsg != null && !validationMsg.isEmpty());
    }
}