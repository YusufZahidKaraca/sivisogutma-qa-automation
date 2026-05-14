Feature: Favorites Search Bar Character Limit Validation

  Scenario: Attempting to fill the favorites search bar with 1000 characters
    Given g21. the user navigates to the product page "https://sivisogutma.com/urun/thermaltake-premium-konsantre-50ml-sivi-kirmizi/"
    When g21. the user clicks on the add to favorites button
    And g21. the user clicks on the go to favorites button
    And g21. the user clicks on the favorites search bar
    And g21. the user tries to enter a 1000 character long text into the search bar
    Then g21. the search bar should not accept the full 1000 characters