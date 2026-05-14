

@18.
Feature: Concurrent Basket Operations

  Scenario: Conflict test between two tabs (Increase vs Remove)
    Given 18. I navigate to "https://sivisogutma.com"

    # Ürünü ekle
    When 18. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 18. I click the add to cart button

    # Eşzamanlı sekme işlemleri
    And 18. I open two tabs and perform conflicting actions
    Then 18. The system should handle the conflict gracefully