Feature: Validation of Mandatory Payment Fields

  Scenario: Attempting to proceed with empty payment information
    Given g44. the user navigates to the product page "https://sivisogutma.com/urun/cavexpc-rahat-sivi-dolum-sisesi-500ml/"
    And g44. the user clicks the add to cart button for the product
    When g44. the user navigates to the payment page "https://sivisogutma.com/odeme/"
    And g44. the user clicks the proceed to payment button without entering info
    Then g44. the payment error message should be displayed successfully