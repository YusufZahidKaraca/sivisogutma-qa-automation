@20.
Feature: Wishlist Remove Operation

  Scenario: Remove product from wishlist and verify empty status
    Given 20. I navigate to "https://sivisogutma.com"
    When 20. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 20. I click the add to wishlist button
    And 20. I go to the wishlist page
    When 20. I remove the product from the wishlist
    Then 20. The wishlist should be empty