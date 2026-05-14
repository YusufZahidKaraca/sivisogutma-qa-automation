

@48.
Feature: Cart Performance Test

  Background: Navigate to the home page
    Given 48. I navigate to "https://sivisogutma.com"

  Scenario: Verify that product is added to cart in under 5 seconds
    When 48. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 48. I measure the time to add product to cart
    Then 48. The elapsed time should be less than 5 seconds