@34.
Feature: Logo Navigation Test

  Scenario: Verify that the header logo redirects to the home page
    Given 34. I navigate to "https://sivisogutma.com/cart/"
    When 34. I click on the header logo
    Then 34. I should be redirected to the home page "https://sivisogutma.com/"