Feature: Duplicate Email Registration

  @Test33
  Scenario: 33 - Attempting to register with an already registered email
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 33. the user enters an already registered email address and a valid password
    And C the user clicks the register button
    Then 33. it is verified that an email already in use error message is displayed