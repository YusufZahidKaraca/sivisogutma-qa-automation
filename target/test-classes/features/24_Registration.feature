Feature: User Registration

  @Test24
  Scenario: 24 - Registering with a real email address
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 24. the user enters a valid email address and password
    And C the user clicks the register button
    Then 24. it is verified that the user is successfully registered