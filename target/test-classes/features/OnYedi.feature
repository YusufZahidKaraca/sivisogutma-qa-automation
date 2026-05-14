@17.
Feature: Cookie and Session Persistence Test

  Scenario: Check if cart items persist in a new tab within the same session
    Given 17. I navigate to "https://sivisogutma.com"
    When 17. I search for product code "Alphacool Core Geforce RTX 4090 Strix + TUF ve Backplate"
    And 17. I click the add to cart button
    # Yeni sekme açıp oradan kontrol ediyoruz
    And 17. I open a new tab and go to the cart page
    Then 17. The product should be added to the cart successfully