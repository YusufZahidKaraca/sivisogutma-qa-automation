Feature: Cross-Tab Cart Synchronization

  Scenario: Verifying cart updates on checkout page when an item is added in a new tab
    Given g39. the user navigates to the first product page "https://sivisogutma.com/urun/coolmoon-argb-pwm-kontrolcu-beyaz/"
    And g39. the user clicks the add to cart button for the first product
    When g39. the user navigates to the checkout page "https://sivisogutma.com/odeme/"
    And g39. the user opens a new tab and navigates to the second product page "https://sivisogutma.com/urun/thermaltake-premium-konsantre-50ml-sivi-asit-yesili/"
    And g39. the user clicks the add to cart button for the second product
    And g39. the user switches back to the checkout tab and refreshes the page
    Then g39. the user should see the second product in the checkout list successfully