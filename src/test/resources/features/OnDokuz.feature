
@19.
Feature: Wishlist Add Operation

  Scenario: Add product to wishlist and verify
    Given 19. I navigate to "https://sivisogutma.com"
    When 19. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 19. I click the add to wishlist button
    Then 19. I should see the product in my wishlist page