@15.
Feature: Stock Persistence and Multiple Product Operations

  Background: Navigate to the home page
    Given 15. I navigate to "https://sivisogutma.com"

  Scenario: Adding additional items after full stock is in cart
    # İlk ürün: Full stok
    When 15. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 15. I add all available items to the cart

    # İkinci ürün: Arama ve 1 adet ekleme
    And 15. I search for product code "Alphacool Eiszapfen Quick Release Coupling Set"
    And 15. I click the add to cart button

    # Sepete git ve sepet içindeki "+" butonuyla miktarı arttır
    And 15. I go to the cart page and click plus button for the first item
    Then 15. The product should be added to the cart successfully