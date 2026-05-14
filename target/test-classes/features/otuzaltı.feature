Feature: Product Description Length Validation

  Scenario: Verifying the product description length is at least 150 characters
    Given g36. the user navigates to the specific product page "https://sivisogutma.com/urun/alphacool-eiszapfen-pressure-valve-g1-4-krom/"
    Then g36. the product description should be loaded and contain at least 150 characters