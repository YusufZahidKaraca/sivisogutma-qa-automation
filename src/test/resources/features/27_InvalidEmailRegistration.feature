Feature: Invalid Email Registration

  @Test27
  Scenario: 27 - Attempting to register with an invalid email address
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 27. the user enters an invalid email address and valid password
    And C the user clicks the register button
    Then 27. it is verified that an invalid email error message is displayed