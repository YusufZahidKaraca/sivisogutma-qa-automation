Feature: Quick View Button Functionality Validation

  Scenario: Verifying the quick view modal opens correctly
    Given g38. the user navigates to the category page "https://sivisogutma.com/"
    When g38. the user clicks on the quick view button of a product
    Then g38. the quick view modal should be displayed successfully