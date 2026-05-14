Feature: Brand Link Redirection Validation

  Scenario: Verifying that the brand link navigates to the correct brand page
    Given g37. the user navigates to the product page "https://sivisogutma.com/urun/alphacool-eiszapfen-pressure-valve-g1-4-krom/"
    When g37. the user clicks on the brand link
    Then g37. the user should see the specific brand element on the brand page