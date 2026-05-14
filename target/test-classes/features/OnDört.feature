

@14.
Feature: Stock Limit and Cart Operations

  Background: Navigate to the home page
    Given 14. I navigate to "https://sivisogutma.com"

  Scenario: Testing stock limit by clicking cart icon
    When 14. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 14. I add all available items to the cart
    And 14. I go to the cart page and try to exceed stock limit
    Then 14. The product should be added to the cart successfully