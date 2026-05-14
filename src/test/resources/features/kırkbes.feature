Feature: Page Performance Validation

  Scenario: Verifying if the page loads in less than 5 seconds
    Given g45. the user measures the load time of the page "https://sivisogutma.com/urun-kategori/araclar/"
    Then g45. the total load time should be under 5000 milliseconds