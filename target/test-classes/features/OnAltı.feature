@16.
Feature: Manual Quantity Input Test

  Background: Navigate to the home page
    Given 16. I navigate to "https://sivisogutma.com"

  Scenario: Testing manual quantity entry in cart page
    # İlk ürün: Arama ve sepete ekleme
    When 16. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 16. I click the add to cart button

    # Sepete git ve miktar alanına manuel 99 yaz
    And 16. I go to the cart page and enter quantity "99" for the first item
    Then 16. The product should be added to the cart successfully
