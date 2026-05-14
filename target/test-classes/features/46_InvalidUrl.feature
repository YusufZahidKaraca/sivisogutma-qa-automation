Feature: Invalid URL Navigation

  @Test46
  Scenario: 46 - Navigating to an invalid URL
    Given C the user navigates to the "https://sivisogutma.com/gecersiz-sayfa-adresi-123" website
    Then 46. it is verified that a 404 page not found error is displayed